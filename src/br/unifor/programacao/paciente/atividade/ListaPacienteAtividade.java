package br.unifor.programacao.paciente.atividade;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.bean.PacienteBean;
import br.unifor.programacao.paciente.dao.PacienteDAO;

public class ListaPacienteAtividade extends Activity {

	public static final String TAG_S = "SELECAO_PACIENTE";
	// Criação de uma classe PacienteBean para utilizar com o DAO.
	private PacienteBean pacienteSelecionado;
	// Criar o ListView de Pacientes da Interface Grafica
	private ListView ListViewPacientes;
	// Criar Lista de Pacientes (objetos Java)
	private List<PacienteBean> listaPacientesBean;
	// Criar o Array Adaptar para conversão de informação dos objetos
	// do ListView para o Java
	private ArrayAdapter<PacienteBean> adaptador;
	// Criar o tipo de layout a ser utilizado nos itens do ListView
	private int adaptadorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.lista_paciente_atividade);

		// Recupera a instancia por meio da classe R.java
		// Associa o ListView com o item definido no layout.xml
		ListViewPacientes =  (ListView) findViewById(R.id.Listagem);
		// Recupera a instancia de PacienteDAO
		PacienteDAO pacienteDAO =  new PacienteDAO(ListaPacienteAtividade.this);
		// Recupera a lista de PacientesBean que foi recupera do registo
		// do banco de dados SQLite
		listaPacientesBean = pacienteDAO.recuperarRegistros();

		if (listaPacientesBean.isEmpty()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			AlertDialog dialog = builder.create();
			dialog.setTitle("Não Existem Pacientes Cadastrados");
			dialog.show();

		}else{
			adaptadorLayout = android.R.layout.simple_expandable_list_item_1;
			adaptador = new ArrayAdapter<PacienteBean> (this,adaptadorLayout, listaPacientesBean);

			ListViewPacientes.setAdapter(adaptador);

			registerForContextMenu(ListViewPacientes);

			// Criacao um evento de clique curto
			ListViewPacientes.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(ListaPacienteAtividade.this, PacienteAtividade.class);
					Parcelable parceable = (Parcelable) ListViewPacientes.getItemAtPosition(position);
					intent.putExtra("paciente",parceable);
					startActivity(intent);
				}		
			});
			// Criação do evento longo que utiliza o MenuContext
			ListViewPacientes.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int posicao, long id) {
					pacienteSelecionado = (PacienteBean) adaptador.getItem(posicao);

					Log.i(TAG_S, "Paciente Selecionado ListView.longClick"
							+ pacienteSelecionado.getNome());
					return false;
				}
			});
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
		getMenuInflater().inflate(R.menu.menu_contexto, menu);
	}

	public boolean onContextItemSelected(MenuItem item){
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.itemRemoverPaciente:
			//Método privado que removeo registro de pacientes
			removerRegistroPaciente();
			break;
			
		case R.id.itemPacienteLiberado:
			//Método privado que atualiza registro de pacientes
			atualizarRegistroPaciente(intent);
			break;

			//Intents Implicitas
		case R.id.itemLigarFamiliar:
			//Método privado que habilita ligação para familiar
			try {
				ligarFamiliaPaciente(intent,pacienteSelecionado);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.itemEnviarSMS:
			//Método privado que habilita SMS para responsável do paciente
			Log.i("Enviar SMS para ",pacienteSelecionado.getCelular());
			enviarSMSResponsavelPaciente(intent,pacienteSelecionado);
			break;
			
		case R.id.itemEnviarEmail:
			//Método privado que envia um email com link para resultados de exames
			Log.i("Enviar Email para ",pacienteSelecionado.getEmail());
			enviarRelatorioExamePorEmail(intent,pacienteSelecionado.getEmail());
			break;
		case R.id.iteminformarDados:

			Intent intent1 = new Intent(ListaPacienteAtividade.this, InformacoesAdicionaisAtividade.class);
			// Parametros passados para a tela de informacoes adicionais
			Long parametro1 = pacienteSelecionado.getId();
			String parametro2 = pacienteSelecionado.getNome();
			intent1.putExtra("id", parametro1);
			intent1.putExtra("nome", parametro2);	
			startActivity(intent1);
			
			break;
			
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void removerRegistroPaciente(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Paciente removido: "+ pacienteSelecionado.getNome());

		builder.setPositiveButton("sim", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				PacienteDAO pacienteDAO = new PacienteDAO(ListaPacienteAtividade.this);
				pacienteDAO.removerRegistroPaciente(pacienteSelecionado);
				pacienteDAO.close();
				pacienteDAO.recuperarRegistros();

				listaPacientesBean.remove(pacienteSelecionado);
				pacienteSelecionado = null;
				adaptador.notifyDataSetChanged();
			}
		});

		builder.setNegativeButton("Não", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmar operação");
		dialog.show();
	}

	private void atualizarRegistroPaciente(Intent intent){

		intent = new Intent(ListaPacienteAtividade.this, PacienteAtividade.class);
		Parcelable parceable = (Parcelable) this.pacienteSelecionado;
		intent.putExtra("paciente",parceable);
		startActivity(intent);
	}

	private void ligarFamiliaPaciente(Intent intent,PacienteBean paciente) throws RemoteException, OperationApplicationException{
		intent = new Intent(Intent.ACTION_CALL);
		// Aqui podemos utilizar o número de telefone do paciente
		intent.setData(Uri.parse("tel: + " + paciente.getTelefone()));
		
		//Cria um contato com os dados: contato de emergência e telefone residencial
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(Data.CONTENT_URI)
                .withValueBackReference(Data.RAW_CONTACT_ID,rawContactInsertIndex)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.DISPLAY_NAME, paciente.getContato()+" Paciente:"+ paciente.getNome()) //Descrição do contato
                .build());
        ops.add(ContentProviderOperation
                .newInsert(Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, paciente.getTelefone()) // Nmero do telerone Residêncial
                .withValue(Phone.TYPE, Phone.TYPE_HOME).build()); // Tipo de número	
        
        
			
        	getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			Toast.makeText(this, "Contato adicionado", Toast.LENGTH_LONG).show();
			
		
        
		// Iniciar a Intent Implicita
		startActivity(intent);
	}

	private void enviarSMSResponsavelPaciente(Intent intent,PacienteBean paciente){
		intent = new Intent(Intent.ACTION_VIEW);
		// Aqui podemos utilizar o número de telefone do paciente
		intent.setData(Uri.parse("sms: + " + paciente.getCelular()));
		intent.putExtra("sms_body", "Informações sobre o paciente "+ paciente.getNome() + " entrar em contato...");
		startActivity(intent);
	}

	private void enviarRelatorioExamePorEmail(Intent intent,String remetente){

		//String remetente ="eguimaraes@unifor.br";
		Log.i("Email paciente",remetente);
		String assunto = "Relatório de Exame";
		String corpoMensagem = "Segue o link para disponibilização dos resultados do exame";

		intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { remetente });
		intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
		intent.putExtra(Intent.EXTRA_TEXT, corpoMensagem);

		// Neste passo é necessário dizer qual o cliente de email
		// irá encaminhar a mensagem. Deve-se selecionar o que estiver
		// disponível no dispositivo Android (ou ADV).
		intent.setType("message/rfc822");
		
		// Somente vai abrir o cliente de email se estiver configurado.
		// Permite ao cliente escolher qual o cliente deve ser utilizado.
		startActivity(Intent.createChooser(intent, "Escolha o Cliente de Email"));

	}
}

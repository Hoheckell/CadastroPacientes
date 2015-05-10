package br.unifor.programacao.paciente.atividade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.bean.PacienteBean;
import br.unifor.programacao.paciente.dao.PacienteDAO;
import br.unifor.programacao.paciente.helper.AdicionaisHelper;

/** 
*
* Atividade de Informações adicionais do paciente acessada através do menu de contexto da lista de pacientes
* @author      Hoheckell
*/

public class InformacoesAdicionaisAtividade extends Activity {
	
	public PacienteBean paciente;
	private EditText editTextAnome;
	private Button btnSave;
	private EditText editTextEndereco;
	private EditText editTextCelular;
	private EditText editTextTelefone;
	private EditText editTextEmail;
	private EditText editTextContato;
	PacienteDAO pdao =  new PacienteDAO(InformacoesAdicionaisAtividade.this);
	
	//Método usado para confirmação e inserção dos dados adicionais
	public void atualizaAdicionais(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(InformacoesAdicionaisAtividade.this);
		builder.setMessage("Deseja confirmar os dados de "+ editTextAnome.getText().toString() + "?");

		builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//Helper para os dados adicionais
				AdicionaisHelper adicionaisHelper = new AdicionaisHelper(InformacoesAdicionaisAtividade.this);
				PacienteBean pacientebean = adicionaisHelper.getPaciente();

				PacienteDAO pacienteDAO = new PacienteDAO(InformacoesAdicionaisAtividade.this);
				Log.i("Dados Adicionais","Inserindo dados adicionais do paciente " + pacientebean.getNome());
				pacienteDAO.atualizarRegistroAdicionais(pacientebean);
				pacienteDAO.close();
				finish();
			}

		});

		builder.setNegativeButton("Não", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle("Confirmar Operação");
		dialog.show();	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacoes_adicionais);
		Log.d("Metodo","oncreate iniciado");

		//Define o botão que salva os dados adicionais
		btnSave = (Button) findViewById(R.id.btnSalvarAdd);
		
		btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {

						atualizaAdicionais();
						
			}
		});
		
		PacienteDAO pacienteDAO =  new PacienteDAO(InformacoesAdicionaisAtividade.this);
				
		editTextAnome = (EditText) findViewById(R.id.editTextAnome);
		editTextEndereco = (EditText) findViewById(R.id.EditTextAendereco);
		editTextCelular = (EditText) findViewById(R.id.EditTextAcelular);
		editTextTelefone = (EditText) findViewById(R.id.EditTextAtelefone);
		editTextEmail = (EditText) findViewById(R.id.EditTextAemail);
		editTextContato = (EditText) findViewById(R.id.editTextAcontato);
		
		//Recupera o ID do paciente enviado da atividade ListaPaciente
		Bundle objeto = getIntent().getExtras();
		
		if (objeto.containsKey("id")){
			
			Long id = objeto.getLong("id");
			String nome = objeto.getString("nome");
			
			editTextAnome.setText(nome);
			
			//Recupera os dados adicionais caso existam
			//para preencher a atividade de informaçõesAdicionais
			paciente = pacienteDAO.recuperaItem(id);	

			Log.i("Dados Adicionais","Recuperando dados adicionais do paciente " + paciente.getNome());
			
			if(paciente.getEndereco() != null){
				editTextEndereco.setText(paciente.getEndereco().toString());
			}
			if(paciente.getCelular() != null){
				editTextCelular.setText(paciente.getCelular().toString());
			}
			if(paciente.getTelefone() != null){
				editTextTelefone.setText(paciente.getTelefone().toString());
			}
			if(paciente.getEmail() != null){
				editTextEmail.setText(paciente.getEmail().toString());
			}
			if(paciente.getContato() != null){
				editTextContato.setText(paciente.getContato().toString());
			}
			
			pacienteDAO.close();
			
		} 
		
		

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.informacoes_adicionais, menu);
		return true;
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
}

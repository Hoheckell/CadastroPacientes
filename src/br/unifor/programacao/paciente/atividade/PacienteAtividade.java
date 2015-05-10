package br.unifor.programacao.paciente.atividade;

import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.bean.PacienteBean;
import br.unifor.programacao.paciente.dao.PacienteDAO;
import br.unifor.programacao.paciente.helper.EdicaoHelper;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class PacienteAtividade extends Activity {

	private EdicaoHelper edicaoHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.paciente_atividade);
		PacienteBean pacienteBean = getIntent().getParcelableExtra("paciente");

		edicaoHelper =  new EdicaoHelper(this,pacienteBean);
		edicaoHelper.getBotaoAtualizar().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				PacienteBean pacienteBean =  edicaoHelper.getPaciente();
				PacienteDAO pacienteDAO = new PacienteDAO(PacienteAtividade.this);
				pacienteDAO.atualizarRegistroPaciente(pacienteBean);
				pacienteDAO.close();
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_paciente, menu);
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

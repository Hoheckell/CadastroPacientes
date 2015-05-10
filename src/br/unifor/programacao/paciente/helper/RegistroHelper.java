package br.unifor.programacao.paciente.helper;

import android.widget.EditText;
import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.atividade.RegistroPacienteAtividade;
import br.unifor.programacao.paciente.bean.PacienteBean;

public class RegistroHelper {

	private EditText editTextPnome;
	private EditText editTextPressao;
	private EditText editTextLeito;
	private EditText editTextBpm;
	private EditText editTexttemp;
	private EditText editTextIntern;
	private EditText editTextSangue;
	
	
	private PacienteBean paciente;
	
	public RegistroHelper(RegistroPacienteAtividade atividade){

		this.editTextPnome = (EditText) atividade.findViewById(R.id.editTextPnome);
		this.editTextPressao = (EditText) atividade.findViewById(R.id.editTextPressao);
		this.editTextLeito = (EditText) atividade.findViewById(R.id.editTextLeito);
		this.editTextBpm = (EditText) atividade.findViewById(R.id.editTextBpm);
		this.editTexttemp = (EditText) atividade.findViewById(R.id.editTexttemp);
		this.editTextIntern = (EditText) atividade.findViewById(R.id.editTextIntern);
		this.editTextSangue = (EditText) atividade.findViewById(R.id.editTextSangue);

		
		this.paciente = this.getPaciente();
	}

	public PacienteBean getPaciente(){
		PacienteBean paciente = new PacienteBean();		
		
		paciente.setNome(editTextPnome.getText().toString());
		paciente.setPressao(editTextPressao.getText().toString());
		paciente.setLeito(editTextLeito.getText().toString());
		paciente.setBPM(editTextBpm.getText().toString());
		paciente.setTemperatura(editTexttemp.getText().toString());
		paciente.setMotivoInternacao(editTextIntern.getText().toString());
		paciente.setTipoSanguineo(editTextSangue.getText().toString());

		return paciente;
	}

}

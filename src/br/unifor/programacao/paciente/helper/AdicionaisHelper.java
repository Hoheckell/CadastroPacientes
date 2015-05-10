package br.unifor.programacao.paciente.helper;

import android.widget.EditText;
import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.atividade.InformacoesAdicionaisAtividade;
import br.unifor.programacao.paciente.bean.PacienteBean;

/** 
 *
 * @param  Activity  Recebe a atividade na qual será usada o Helper
 * @author      Hoheckell
 * @version 1.0
 * Classe AdicionaisHelper esta classe é usada como Helper na classe InformacoesAdicionaisAtividade
 */

public class AdicionaisHelper {
	
	private EditText editTextendereco;
	private EditText editTextcelular;
	private EditText editTexttelefone;
	private EditText editTextemail;
	private EditText editTextcontato;
	private Long IdPac;

	private PacienteBean paciente;
	
	public AdicionaisHelper(InformacoesAdicionaisAtividade atividade){

		this.editTextendereco = (EditText) atividade.findViewById(R.id.EditTextAendereco);
		this.editTextcelular = (EditText) atividade.findViewById(R.id.EditTextAcelular);
		this.editTexttelefone = (EditText) atividade.findViewById(R.id.EditTextAtelefone);
		this.editTextemail = (EditText) atividade.findViewById(R.id.EditTextAemail);
		this.editTextcontato = (EditText) atividade.findViewById(R.id.editTextAcontato);
		IdPac = atividade.paciente.getId();
		this.paciente = this.getPaciente();
		
	}

	public PacienteBean getPaciente(){
		PacienteBean paciente = new PacienteBean();		
		
		paciente.setId(IdPac);
		paciente.setEndereco(editTextendereco.getText().toString());
		paciente.setCelular(editTextcelular.getText().toString());
		paciente.setTelefone(editTexttelefone.getText().toString());
		paciente.setEmail(editTextemail.getText().toString());
		paciente.setContato(editTextcontato.getText().toString());
		return paciente;
		
	}

}

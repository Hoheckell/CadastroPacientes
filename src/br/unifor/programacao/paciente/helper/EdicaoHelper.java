package br.unifor.programacao.paciente.helper;

import android.widget.Button;
import android.widget.EditText;
import br.unifor.programacao.paciente.R;
import br.unifor.programacao.paciente.atividade.PacienteAtividade;
import br.unifor.programacao.paciente.bean.PacienteBean;

public class EdicaoHelper {

	private EditText editTextPnome;
	private EditText editTextPpressao;
	private EditText editTextPleito;
	private EditText editTextPbpm;
	private EditText editTextPtemperatura;
	private EditText editTextPintern;
	private EditText editTextPsangue;
	public Button botaoAtualizar;
	private Long Idp;

	private PacienteBean paciente;

	public EdicaoHelper(PacienteAtividade atividade,PacienteBean extras){

		this.editTextPnome = (EditText) atividade.findViewById(R.id.editTextPnome);
		this.editTextPpressao = (EditText) atividade.findViewById(R.id.editTextPpressao);
		this.editTextPleito = (EditText) atividade.findViewById(R.id.editTextPleito);
		this.editTextPbpm = (EditText) atividade.findViewById(R.id.editTextPbpm);
		this.editTextPtemperatura = (EditText) atividade.findViewById(R.id.editTextPtemp);
		this.editTextPintern = (EditText) atividade.findViewById(R.id.editTextPintern);
		this.editTextPsangue = (EditText) atividade.findViewById(R.id.editTextPsangue);
		this.botaoAtualizar = (Button) atividade.findViewById(R.id.buttonUpd);

		Idp = extras.getId();
		editTextPnome.setText(extras.getNome());
		editTextPpressao.setText(extras.getTemperatura());
		editTextPleito.setText(extras.getBPM());
		editTextPbpm.setText(extras.getLeito());
		editTextPtemperatura.setText(extras.getPressao());
		editTextPintern.setText(extras.getSanguineo());
		editTextPsangue.setText(extras.getMotivoInternacao());

		paciente = this.getPaciente();

	}

	public PacienteBean getPaciente() {

		PacienteBean paciente = new PacienteBean();

		paciente.setId(Idp);
		paciente.setNome(editTextPnome.getText().toString());
		paciente.setPressao(editTextPpressao.getText().toString());
		paciente.setLeito(editTextPleito.getText().toString());
		paciente.setBPM(editTextPbpm.getText().toString());
		paciente.setTemperatura(editTextPtemperatura.getText().toString());
		paciente.setMotivoInternacao(editTextPintern.getText().toString());
		paciente.setTipoSanguineo(editTextPsangue.getText().toString());

		return paciente;
	}

	public EditText getEditTextPnome() {
		return editTextPnome;
	}

	public void setEditTextPnome(EditText editTextPnome) {
		this.editTextPnome = editTextPnome;
	}

	public EditText getEditTextPpressao() {
		return editTextPpressao;
	}

	public void setEditTextPpressao(EditText editTextPpressao) {
		this.editTextPpressao = editTextPpressao;
	}

	public EditText getEditTextPleito() {
		return editTextPleito;
	}

	public void setEditTextPleito(EditText editTextPleito) {
		this.editTextPleito = editTextPleito;
	}

	public EditText getEditTextPbpm() {
		return editTextPbpm;
	}

	public void setEditTextPbpm(EditText editTextPbpm) {
		this.editTextPbpm = editTextPbpm;
	}

	public EditText getEditTextPtemperatura() {
		return editTextPtemperatura;
	}

	public void setEditTextPtemperatura(EditText editTextPtemperatura) {
		this.editTextPtemperatura = editTextPtemperatura;
	}

	public EditText getEditTextPintern() {
		return editTextPintern;
	}

	public void setEditTextPintern(EditText editTextPintern) {
		this.editTextPintern = editTextPintern;
	}

	public EditText getEditTextPsangue() {
		return editTextPsangue;
	}

	public void setEditTextPsangue(EditText editTextPsangue) {
		this.editTextPsangue = editTextPsangue;
	}

	public Button getBotaoAtualizar() {
		return botaoAtualizar;
	}

	public void setBotaoAtualizar(Button botaoAtualizar) {
		this.botaoAtualizar = botaoAtualizar;
	}

	public Long getIdp() {
		return Idp;
	}

	public void setIdp(Long idp) {
		Idp = idp;
	}

	public void setPaciente(PacienteBean paciente) {
		this.paciente = paciente;
	}

}

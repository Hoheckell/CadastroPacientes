package br.unifor.programacao.paciente.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PacienteBean implements Parcelable{

	private Long id;
	private String nome;
	private String leito;
	private String bpm;
	private String pressao;
	private String tipoSanguineo;
	private String temperatura;
	private String motivoInternacao;
	private String endereco;
	private String celular;
	private String telefone;
	private String email;
	private String contato;
	

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public PacienteBean() {
		// TODO Auto-generated constructor stub
	}

	public PacienteBean(Parcel in) {  
		readFromParcelable(in);  
	}  

	private void readFromParcelable(Parcel in) {
		id = in.readLong();
		nome = in.readString();
		leito = in.readString();
		bpm = in.readString();
		pressao = in.readString();
		tipoSanguineo = in.readString();
		temperatura = in.readString();
		motivoInternacao = in.readString();
		endereco = in.readString();
		celular = in.readString();
		telefone = in.readString();
		email = in.readString();
		contato = in.readString();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLeito() {
		return leito;
	}
	public void setLeito(String leito) {
		this.leito = leito;
	}
	public String getBPM() {
		return bpm;
	}
	public void setBPM(String bpm) {
		this.bpm = bpm;
	}
	public String getPressao() {
		return pressao;
	}
	public void setPressao(String pressao) {
		this.pressao = pressao;
	}
	public String getSanguineo() {
		return tipoSanguineo;
	}
	public void setTipoSanguineo(String sangue) {
		this.tipoSanguineo = sangue;
	}
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getMotivoInternacao() {
		return motivoInternacao;
	}
	public void setMotivoInternacao(String mIntern) {
		this.motivoInternacao = mIntern;
	}

	@Override
	public String toString() {
		return "Nome:" + this.nome +" Leito:" + this.leito ;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(nome);
		dest.writeString(bpm);
		dest.writeString(leito);
		dest.writeString(temperatura);
		dest.writeString(motivoInternacao);
		dest.writeString(pressao);
		dest.writeString(tipoSanguineo);
		dest.writeString(endereco);
		dest.writeString(celular);
		dest.writeString(telefone);
		dest.writeString(email);
		dest.writeString(contato);
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {  
		public PacienteBean createFromParcel(Parcel in) {  
			return new PacienteBean(in);  
		}  

		public PacienteBean[] newArray(int size) {  
			return new PacienteBean[size];  
		}  
	};  

}

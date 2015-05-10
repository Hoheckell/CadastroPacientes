package br.unifor.programacao.paciente.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.unifor.programacao.paciente.bean.PacienteBean;

public class PacienteDAO extends SQLiteOpenHelper {

	public static final int VERSAO = 1;
	public static final String TABELA = "pacientes";
	public static final String DATABASE = "BD_PACIENTES";

	private static final String TAG_I = "INSERIR_PACIENTE";
	private static final String TAG_L = "LISTAR_PACIENTES";
	private static final String TAG_R = "REMOVER_PACIENTE";

	public PacienteDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA 
				+ "('id' INTEGER PRIMARY KEY NOT NULL , "
				+ "'nome' TEXT NOT NULL"
				+ ", 'pressao' TEXT NOT NULL"
				+ ", 'leito' TEXT NOT NULL "
				+ ",'bpm' TEXT NOT NULL"
				+ ",'temperatura' TEXT NOT NULL"
				+ ",'internacao' TEXT NOT NULL"
				+ ",'tipo_sangue' TEXT NOT NULL"
				+ ",'endereco' TEXT NULL"
				+ ",'celular' TEXT NULL"
				+ ",'telefone' TEXT NULL"
				+ ",'email' TEXT NULL"
				+ ",'contato' TEXT NULL"
				+ ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql = "DROP TABLE IF EXISTS "+ TABELA;
		db.execSQL(sql);
		onCreate(db);
	}

	//INSERT
	public void registrarPaciente(PacienteBean paciente){

		ContentValues valores = new ContentValues();

		valores.put("leito", paciente.getLeito().toString());
		valores.put("nome", paciente.getNome().toString());
		valores.put("tipo_sangue",paciente.getSanguineo().toString());
		valores.put("pressao", paciente.getPressao().toString());
		valores.put("temperatura",paciente.getTemperatura().toString());
		valores.put("bpm", paciente.getBPM().toString());
		valores.put("internacao",paciente.getMotivoInternacao().toString());

		getWritableDatabase().insert(TABELA, null, valores);

		Log.i(TAG_I, "Registro realizado: "+ paciente.getNome());

	}

	//UPDATE
	public void atualizarRegistroPaciente(PacienteBean paciente){

		ContentValues valores = new ContentValues();

		valores.put("nome", paciente.getNome().toString());
		valores.put("pressao", paciente.getPressao().toString());
		valores.put("leito", paciente.getLeito().toString());
		valores.put("bpm", paciente.getBPM().toString());
		valores.put("temperatura",paciente.getTemperatura().toString());
		valores.put("internacao",paciente.getMotivoInternacao().toString());
		valores.put("tipo_sangue",paciente.getSanguineo().toString());

		String[] args = new String[]{Long.toString(paciente.getId())};

		getWritableDatabase().update(TABELA, valores, "id=?", args);

		Log.i(TAG_I, "Paciente atualizado: "+ paciente.getNome());

	}
	
	//faz o insert das informações adicionais - UPDATE
	public void atualizarRegistroAdicionais(PacienteBean paciente){

		ContentValues valores = new ContentValues();
		Log.i(TAG_I, "Paciente Adicionais atualizado: "+ paciente.getNome());
		valores.put("endereco", paciente.getEndereco().toString());
		valores.put("celular", paciente.getCelular().toString());
		valores.put("telefone", paciente.getTelefone().toString());
		valores.put("email", paciente.getEmail().toString());
		valores.put("contato",paciente.getContato().toString());

		String[] args = new String[]{Long.toString(paciente.getId())};

		try{
			getWritableDatabase().update(TABELA, valores, "id=?", args);
		}catch(Exception e){

			Log.i(TAG_I, "Erro ao atualizar... " + e.getMessage());
		}

	}

	//SELECT *
	public List<PacienteBean> recuperarRegistros(){

		List<PacienteBean> listaPacientes = new ArrayList<PacienteBean>();

		String sql = "Select * from pacientes";

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		try{
			while(cursor.moveToNext()){

				PacienteBean paciente = new PacienteBean();

				paciente.setId(cursor.getLong(0));
				paciente.setNome(cursor.getString(1));
				paciente.setPressao(cursor.getString(2));
				paciente.setLeito(cursor.getString(3));
				paciente.setBPM(cursor.getString(4));
				paciente.setTemperatura(cursor.getString(5));
				paciente.setMotivoInternacao(cursor.getString(6));
				paciente.setTipoSanguineo(cursor.getString(7));
				paciente.setEndereco(cursor.getString(8));
				paciente.setCelular(cursor.getString(9));
				paciente.setTelefone(cursor.getString(10));
				paciente.setEmail(cursor.getString(11));
				paciente.setContato(cursor.getString(12));

				listaPacientes.add(paciente);
			}
		}catch(SQLException sqle){
			Log.e(TAG_L, sqle.getMessage());
		}finally{
			cursor.close();
		}

		return listaPacientes;
	}
	
	//SELECT WHERE
	public PacienteBean recuperaItem(Long id){

		String sql = "Select * from pacientes where id = " + id ;

		Cursor cursor = getReadableDatabase().rawQuery(sql, null);

		PacienteBean paciente = new PacienteBean();
			
		while(cursor.moveToNext()){
				

				paciente.setId(cursor.getLong(0));
				paciente.setNome(cursor.getString(1));
				paciente.setPressao(cursor.getString(2));
				paciente.setLeito(cursor.getString(3));
				paciente.setBPM(cursor.getString(4));
				paciente.setTemperatura(cursor.getString(5));
				paciente.setMotivoInternacao(cursor.getString(6));
				paciente.setTipoSanguineo(cursor.getString(7));
				paciente.setEndereco(cursor.getString(8));
				paciente.setCelular(cursor.getString(9));
				paciente.setTelefone(cursor.getString(10));
				paciente.setEmail(cursor.getString(11));
				paciente.setContato(cursor.getString(12));

				
		}
		Log.i(TAG_L, "Paciente recuperado: "+ paciente.getNome());
		return paciente;
			
		
	}

	public void removerRegistroPaciente(PacienteBean paciente){
		String [] args = {paciente.getId().toString()};

		getWritableDatabase().delete(TABELA, "id=?", args);

		Log.i(TAG_R, "Paciente removido: "+ paciente.getNome());
	}

}

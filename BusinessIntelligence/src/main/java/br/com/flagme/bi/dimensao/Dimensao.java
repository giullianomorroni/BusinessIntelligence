package br.com.flagme.bi.dimensao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Dimensao {

	Long proximoId(Connection conexao);

	Long inserirRegistro(Connection conexao) throws SQLException;

	Long consultaId(Connection conexao);

	Long consultaIdComInsercao(Connection conexao);

}

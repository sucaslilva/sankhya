package br.com.devsnk.utils;

import java.math.BigDecimal;
import java.util.List;

import br.com.sankhya.jape.EntityFacade;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.vo.EntityVO;
import br.com.sankhya.modelcore.MGEModelException;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;

/* ============================================
 * Lib contendo métodos mais utilizados em 
 * personalizações de clientes Sankhya
 * Autores: Lucas Silva e Matheus Prado
 *  ===========================================*/
public class configuracao {
	public String montaLinkTela(BigDecimal id, String resourceId) throws MGEModelException {
		// id: Id do Log gerado
		// resourceId: Caminho da tela
		String prefixo = "#app";
		String mascara = "{\"ID\":\"" + id + "\"}";

		String parametrosTela = String.format(mascara, id);

		String tela = java.util.Base64.getEncoder().encodeToString(resourceId.getBytes());
		String parametros = java.util.Base64.getEncoder().encodeToString(parametrosTela.getBytes());

		String link = prefixo + "/" + tela + "/" + parametros;

		return link;
	}

	public void gravaAviso(BigDecimal importancia, String titulo, String descricao, String solucao, String gruOrUser,
			List<BigDecimal> codigo) throws Exception {

		// importancia: Grau de importância do aviso;
		// titulo: Título do aviso;
		// descricao: Descrição do aviso;
		// solucao: Solução do aviso;
		// gruOrUser: Definição de quem irá receber o aviso, grupo de usuário = 'G' ou
		// usuário = 'U';
		// codigo: Lista contendo o usuário ou grupo que receberá o aviso;

		for (BigDecimal opcao : codigo) {
			EntityFacade dwfEntityFacade = EntityFacadeFactory.getDWFFacade();
			DynamicVO avisoSistema = (DynamicVO) dwfEntityFacade.getDefaultValueObjectInstance("AvisoSistema");
			avisoSistema.setProperty("IDENTIFICADOR", "PERSONALIZADO");
			avisoSistema.setProperty("IMPORTANCIA", importancia);
			avisoSistema.setProperty("TITULO", titulo);
			avisoSistema.setProperty("DESCRICAO", descricao);
			avisoSistema.setProperty("SOLUCAO", solucao);
			avisoSistema.setProperty("TIPO", "P");
			if (gruOrUser.equals("G"))
				avisoSistema.setProperty("CODGRUPO", opcao);
			if (gruOrUser.equals("U"))
				avisoSistema.setProperty("CODUSU", opcao);
			dwfEntityFacade.createEntity("AvisoSistema", (EntityVO) avisoSistema);
			avisoSistema.setProperty("NUAVISO", null);
		}

	}
}

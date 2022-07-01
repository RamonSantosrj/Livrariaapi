package br.com.livrariaapi.Helpers;

import javax.servlet.http.HttpServletResponse;

import br.com.livrariaapi.security.TokenSecurity;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class FindEmailTokenHelper {

	public static String findEmailToken(HttpServletResponse request) {
		
		String accessToken = request.getHeader("Authorization").replace("Bearer", "").trim();
		String cpf = TokenSecurity.getLoginFromToken(accessToken);
		
		return cpf;
	}
}

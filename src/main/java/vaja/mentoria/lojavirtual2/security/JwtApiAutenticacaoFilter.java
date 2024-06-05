package vaja.mentoria.lojavirtual2.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/* Filtro onde todas as requisicoes serão capturadas para autenticar */
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            /* Estabelece a autenticação do usuário */
            Authentication authentication = new JWTTokenAutenticacaoService()
                    .getAuthetication((HttpServletRequest) request, (HttpServletResponse) response);

            /* Coloca o processo de autenticação para o Spring Security */
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            ((HttpServletResponse) response).getWriter().write("Ocorreu um erro no sistema, entre em contato com o ADM: \n" + e.getMessage());
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

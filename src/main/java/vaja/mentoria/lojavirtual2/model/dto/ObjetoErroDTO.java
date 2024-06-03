package vaja.mentoria.lojavirtual2.model.dto;

import java.io.Serializable;

public class ObjetoErroDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String error;
		private String code;
		public String getError() {
			return error;
		}
		public void setError(String errorString) {
			this.error = error;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String codeString) {
			this.code = code;
		}
		
		

}

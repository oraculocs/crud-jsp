package beans;

public class Telefones {
	
	private Long id;
	private String numero;
	private String tipo;
	private Long usuario;
	
	public Telefones() {
		super();
	}
	
	public Telefones(Long id, String numero, String tipo, Long usuario) {
		super();
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.usuario = usuario;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Telefones [id=" + id + ", numero=" + numero + ", tipo=" + tipo + ", usuario=" + usuario + "]";
	}
	
	
	
	
	

}

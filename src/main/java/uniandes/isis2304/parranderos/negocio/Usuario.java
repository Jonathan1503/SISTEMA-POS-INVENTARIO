package uniandes.isis2304.parranderos.negocio;

public class Usuario implements VOUsuario{
	
	private long id;
	private String nombre;
	private String correo;
	private String contrasena;
	private String tipodoc;
	private long numerodoc;
	private long tipousuario;
	private long sucursal;
	
	
	public Usuario() {
		this.id=0;
		this.nombre="";
		this.correo="";
		this.tipodoc="";
		this.numerodoc=0;
		this.tipousuario=0;
		this.sucursal=0;
		this.contrasena="";
	}
	
	public Usuario(long id,String nombre,String correo,String contrasena,String tipodoc,long numerodoc,long tipousuario,long sucursal) {
		this.id=id;
		this.nombre=nombre;
		this.correo=correo;
		this.tipodoc=tipodoc;
		this.numerodoc=numerodoc;
		this.tipousuario=tipousuario;
		this.sucursal=sucursal;
		this.contrasena= contrasena;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public long getNumerodoc() {
		return numerodoc;
	}

	public void setNumerodoc(long numerodoc) {
		this.numerodoc = numerodoc;
	}

	public long getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(long tipousuario) {
		this.tipousuario = tipousuario;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}
	

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasena=" + contrasena
				+ ", tipodoc=" + tipodoc + ", numerodoc=" + numerodoc + ", tipousuario=" + tipousuario + ", sucursal="
				+ sucursal + "]";
	}
	
	

}

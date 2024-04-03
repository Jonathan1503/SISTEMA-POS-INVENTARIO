package uniandes.isis2304.parranderos.negocio;

public class ClienteConsulta {
	
	private long id;
	private double puntos;
	private String tipoCliente;
	private String direccion;
	private String nombre;
	private String correo;
	private String tipodoc;
	private long numerodoc;
	private String razon;
	
	public ClienteConsulta(long id, double puntos, String tipoCliente, String direccion, String nombre, String correo,
			String tipodoc, long numerodoc,String razon) {
		super();
		this.id = id;
		this.puntos = puntos;
		this.tipoCliente = tipoCliente;
		this.direccion = direccion;
		this.nombre = nombre;
		this.correo = correo;
		this.tipodoc = tipodoc;
		this.numerodoc = numerodoc;
		this.razon= razon;
	}
	
	public ClienteConsulta() {
		super();
		this.id = 0;
		this.puntos = 0;
		this.tipoCliente = "";
		this.direccion = "";
		this.nombre = "";
		this.correo = "";
		this.tipodoc = "";
		this.numerodoc = 0;
		this.razon= "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPuntos() {
		return puntos;
	}

	public void setPuntos(double puntos) {
		this.puntos = puntos;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	@Override
	public String toString() {
		return "ClienteConsulta [id=" + id + ", puntos=" + puntos + ", tipoCliente=" + tipoCliente + ", direccion="
				+ direccion + ", nombre=" + nombre + ", correo=" + correo + ", tipodoc=" + tipodoc + ", numerodoc="
				+ numerodoc + ", razon=" + razon + "]";
	}
	
	
	
	

}

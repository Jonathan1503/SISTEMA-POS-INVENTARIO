package uniandes.isis2304.parranderos.negocio;

public class Sucursal implements VOSucursal {
	
	private long id;
	private String ciudad;
	private String direccion;
	private String nombre;
	
	public Sucursal() {
		this.id=0;
		this.ciudad="";
		this.direccion="";
		this.nombre="";
	}
	
	public Sucursal(long id,String ciudad, String direccion,String nombre ) {
		this.id=id;
		this.ciudad=ciudad;
		this.direccion=direccion;
		this.nombre=nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
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
	
	public String toString() {
		return "Sucursal [id="+id+", ciudad="+ciudad+", direccion="+direccion+", nombre="+nombre+"]";
	}

}

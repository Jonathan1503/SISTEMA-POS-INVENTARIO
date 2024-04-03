package uniandes.isis2304.parranderos.negocio;

public class RolUsuario implements VORolUsuario {
	
	private long id;
	private String nombre;
	
	public RolUsuario(){
		
		this.id= 0;
		this.nombre= "";	
	}
	
	public RolUsuario(long id, String nombre){
		this.id= id;
		this.nombre= nombre;	
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
	
	public String toString() {
		return "RolUsuario [id="+id+", nombre="+nombre+"]";
	}

}

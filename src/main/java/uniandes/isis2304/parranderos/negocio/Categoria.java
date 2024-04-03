package uniandes.isis2304.parranderos.negocio;

public class Categoria implements VOCategoria {
	
	private long id;
	private String nombre;
	
	public Categoria() {
		this.id=0;
		this.nombre="";
	}
	
	public Categoria(long id, String nombre) {
		this.id=id;
		this.nombre=nombre;
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
		return "Categoria [id="+id+", nombre="+nombre+"]";
	}

}

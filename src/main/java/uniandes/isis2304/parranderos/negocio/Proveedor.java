package uniandes.isis2304.parranderos.negocio;

public class Proveedor implements VOProveedor {
	
	public long id;
	public long nit;
	public String nombre;
	public long sucursal;
	
	public Proveedor() {
		this.id=0;
		this.nit=0;
		this.nombre="";
		this.sucursal=0;
	}
	
	public Proveedor(long id,long nit,String nombre,long sucursal) {
		this.id=id;
		this.nit=nit;
		this.nombre=nombre;
		this.sucursal=sucursal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNit() {
		return nit;
	}

	public void setNit(long nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}
	
	public String toString() {
		return "Proveedor [id="+id+", nit="+nit+", nombre="+nombre+", sucursal="+sucursal+"]";
	}
	

}

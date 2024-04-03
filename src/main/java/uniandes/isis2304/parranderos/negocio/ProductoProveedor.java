package uniandes.isis2304.parranderos.negocio;

public class ProductoProveedor implements VOProductoProveedor {
	
	private long idproducto;
	private long idproveedor;
	private String nombre;
	private double precio;
	private double calificacion;
	
	public ProductoProveedor() {
		this.idproducto=0;
		this.idproveedor=0;
		this.nombre="";
		this.precio=0;
		this.calificacion=0;
	}

	public ProductoProveedor(long idproducto, long idproveedor, String nombre, double precio, double calificacion) {
		this.idproducto = idproducto;
		this.idproveedor = idproveedor;
		this.nombre = nombre;
		this.precio = precio;
		this.calificacion = calificacion;
	}

	public long getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}

	public long getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(long idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}
	
	public String toString() {
		return "ProductoProveedor[idproducto="+idproducto+", idproveedor="+idproveedor+", nombre="+nombre+", precio="+
				precio+", calificacion="+calificacion;
	}
	

}

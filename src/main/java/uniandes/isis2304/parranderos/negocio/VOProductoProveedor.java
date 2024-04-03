package uniandes.isis2304.parranderos.negocio;

public interface VOProductoProveedor {
	
	public long getIdproducto();
	
	public long getIdproveedor();
	
	public String getNombre();
	
	public double getPrecio();
	
	public double getCalificacion();
	
	@Override
	public String toString();

}

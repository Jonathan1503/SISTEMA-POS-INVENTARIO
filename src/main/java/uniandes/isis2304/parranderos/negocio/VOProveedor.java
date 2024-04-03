package uniandes.isis2304.parranderos.negocio;

public interface VOProveedor {
	
	public long getId();
	
	public long getNit();
	
	public long getSucursal();
	
	public String getNombre();
	
	@Override
	public String toString();

}

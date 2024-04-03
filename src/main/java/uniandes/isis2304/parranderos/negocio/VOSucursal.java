package uniandes.isis2304.parranderos.negocio;

public interface VOSucursal {
	
	public long getId();
	
	public String getCiudad();
	
	public String getDireccion();
	
	public String getNombre();
	
	@Override
	public String toString();
}

package uniandes.isis2304.parranderos.negocio;

public interface VOUsuario {
	
	public long getId();
	
	public String getNombre();
	
	public String getCorreo();
	
	public String getContrasena();
	
	public String getTipodoc();
	
	public long getNumerodoc();
	
	public long getTipousuario();
	
	public long getSucursal();
	
	@Override
	public String toString();
	
	

}

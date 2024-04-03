package uniandes.isis2304.parranderos.negocio;

public interface VOCliente {
	
	public long getId();
	public double getPuntos();
	public String getTipoCliente();
	public String getDireccion();
	public String getNombre();
	public String getCorreo();
	public String getTipodoc();
	public long getNumerodoc();
	@Override
	public String toString();
	

}

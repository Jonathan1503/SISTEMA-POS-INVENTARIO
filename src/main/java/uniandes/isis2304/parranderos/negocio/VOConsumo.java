package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOConsumo {
	
	public long getId();
	public double getPuntos();
	public String getTipoCliente();
	public String getDireccion();
	public String getNombre();
	public String getCorreo();
	public String getTipodoc();
	public long getNumerodoc();
	public int getCantidad();
	public Timestamp getFechaventa();

}

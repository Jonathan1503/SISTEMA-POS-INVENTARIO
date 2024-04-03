package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public interface VOProducto {
	
	public long getId();
	public String getNombre();
	public long getIdCategoria();
	public String getMarca();
	public double getPrecioUnitario();
	public long getCantidad();
	public String getUnidadMedida();
	public double getPrecioXUndMedida();
	public String getPresentacion();
	public double getPeso();
	public double getVolumen();
	public String getCodigoDeBarras();
	public long getNivelReorden();
	public String getSubcategoria();
	public Timestamp getFechaVencimiento();
	@Override
	public String toString();

}

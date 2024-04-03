package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public interface VOPromocion {
	
	public long getId();
	public long getIdproducto();
	public long getIdsucursal();
	public String getTipo();
	public long getCantidad();
	public  Timestamp getFechaInicio();
	public  Timestamp getFechaFin();
	@Override
	public String toString();

}

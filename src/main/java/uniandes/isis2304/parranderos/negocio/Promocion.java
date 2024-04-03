package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public class Promocion implements VOPromocion{
	
	private long id;
	private long idproducto;
	private long idsucursal;
	private String tipo;
	private long cantidad;
	private  Timestamp fechaInicio;
	private  Timestamp fechaFin;
	
	public Promocion() {
		
		this.id = 0;
		this.idproducto = 0;
		this.idsucursal = 0;
		this.tipo = "";
		this.cantidad = 0;
		this.fechaInicio = new  Timestamp(0);
		this.fechaFin = new  Timestamp(0);
	}
	
	public Promocion(long id, long idproducto, long idsucursal, String tipo, long cantidad,  Timestamp fechaInicio,
			 Timestamp fechaFin) {
		
		this.id = id;
		this.idproducto = idproducto;
		this.idsucursal = idsucursal;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(long idproducto) {
		this.idproducto = idproducto;
	}

	public long getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(long idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public  Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio( Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public  Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin( Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public String toString() {
		return "Promocion [id="+id+", idproducto="+idproducto+", idsucursal="+idsucursal+", tipo="+tipo+", cantidad="+cantidad+
				", fechaInicio="+fechaInicio+", fechaFin="+fechaFin+"]";
	}
	
	
	



}

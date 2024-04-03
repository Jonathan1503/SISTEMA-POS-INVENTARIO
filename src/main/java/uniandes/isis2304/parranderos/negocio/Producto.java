package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;

public class Producto implements VOProducto {
	
	private long id;
	private String nombre;
	private long idCategoria;
	private String marca;
	private double precioUnitario;
	private long cantidad;
	private String unidadMedida;
	private double precioXUndMedida;
	private String presentacion;
	private double peso;
	private double volumen;
	private String codigoDeBarras;
	private long nivelReorden;
	private String subcategoria;
	private Timestamp fechaVencimiento;
	
	public Producto() {
		this.id=0;
		this.nombre="";
		this.idCategoria=0;
		this.marca="";
		this.precioUnitario=0;
		this.cantidad=0;
		this.unidadMedida="";
		this.precioXUndMedida=0;
		this.presentacion="";
		this.peso=0;
		this.volumen=0;
		this.codigoDeBarras="";
		this.nivelReorden=0;
		this.subcategoria="";
		this.fechaVencimiento= new Timestamp (0);
	}

	public Producto(long id, String nombre, long idCategoria, String marca, double precioUnitario, long cantidad,
			String unidadMedida, double precioXUndMedida, String presentacion, double peso, double volumen,
			String codigoDeBarras, long nivelReorden, String subcategoria, Timestamp fechaVencimiento) {
		
		this.id = id;
		this.nombre = nombre;
		this.idCategoria = idCategoria;
		this.marca = marca;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.unidadMedida = unidadMedida;
		this.precioXUndMedida = precioXUndMedida;
		this.presentacion = presentacion;
		this.peso = peso;
		this.volumen = volumen;
		this.codigoDeBarras = codigoDeBarras;
		this.nivelReorden = nivelReorden;
		this.subcategoria = subcategoria;
		this.fechaVencimiento = fechaVencimiento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public long getCantidad() {
		return cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public double getPrecioXUndMedida() {
		return precioXUndMedida;
	}

	public void setPrecioXUndMedida(double precioXUndMedida) {
		this.precioXUndMedida = precioXUndMedida;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public long getNivelReorden() {
		return nivelReorden;
	}

	public void setNivelReorden(long nivelReorden) {
		this.nivelReorden = nivelReorden;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Timestamp getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String toString() {
		return "Producto [id="+id+", idCategoria="+idCategoria+", marca="+marca+", precioUnitario="+precioUnitario
				+", cantidad="+cantidad+", unidadMedida="+unidadMedida+", precioXUndMedida="+precioXUndMedida+
				", presentacion="+presentacion+", peso="+peso+", volumen="+volumen+", codigoDeBarras="+codigoDeBarras
				+", nivelReorden="+nivelReorden+", subcategoria="+subcategoria+", fechaVencimiento="+fechaVencimiento+
				"]";
	}
	

}

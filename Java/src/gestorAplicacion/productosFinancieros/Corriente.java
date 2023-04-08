/* Clase Corriente
 *
 * Siendo una clase hija de Cuenta, se diferencia por incluir la capacidad de sobregirarse, de tal forma que el usuario puede 
 * realizar transferencias de mayor cantidad que las de su saldo.
 *
 */
package gestorAplicacion.productosFinancieros;

import java.util.ArrayList;
import java.io.Serializable;
import gestorAplicacion.infoClientes.Banco;
import gestorAplicacion.infoClientes.Transaccion;
import gestorAplicacion.infoClientes.Usuario;

public class Corriente extends Cuenta implements Serializable{
	private static final float capacidadSobregiro = 600000;
	private float sobregiroActual;
	
    // El Array de clase de clientes de encarga de guardar todas las instancias de
    // Cliente para poder guardar y cargarlas en la serializacion
	private static ArrayList<Corriente> corriente = new ArrayList<>();
	
    // Constructor
	public Corriente(int nroCuenta, Usuario titular,float saldo,Banco banco,String tipoDeCuenta,float sobregiroActual) {
		super(nroCuenta,titular,saldo,banco,tipoDeCuenta);
		this.sobregiroActual = sobregiroActual;
		banco.getListaCuentas().add(this);
		titular.getCuentasAsociadas().add(this);
		
		corriente.add(this);
	}

	// Verifica si el usuario cuenta con saldo y capacidad de sobregiro para realizar la transacción. De ser así setea el
    // sobregiroActual en caso de haberse usado, realiza los ajustes de saldo en cada cuenta, crea el objeto transaccion y lo
    // añade al historial de las cuentas involucradas.
	public boolean transferir(Cuenta cuentaDestino, float valorTransferencia) {

		if(this.saldo+(this.capacidadSobregiro-this.sobregiroActual)>= valorTransferencia) {

			if(valorTransferencia>this.saldo) {
				setSaldo(0);
				setSobregiroActual(this.sobregiroActual+(valorTransferencia-this.saldo));
			}
			else{
				this.restarSaldo(valorTransferencia);
			}

			cuentaDestino.sumarSaldo(valorTransferencia);
			Transaccion trans = new Transaccion(this,cuentaDestino,valorTransferencia);
			historialTransferencia.add(trans);
			cuentaDestino.historialTransferencia.add(trans);
			return true;
		}
		else {
			return false;
		}
	}
	
	// Verifica si el usuario cuenta con saldo y capacidad de sobregiro para realizar el pago de la cuota mensual. De ser así setea
    // el sobregiroActual en caso de haberse usado; realiza los ajustes de saldo en la cuenta origen y la deuda del crédito; 
    // crea el objeto transaccion y lo añade al historial de la cuenta origen
	public boolean transferir(Credito credito) {
		if(this.saldo+(this.capacidadSobregiro-this.sobregiroActual)>= credito.getCuotaMensual()) {
			if(credito.getCuotaMensual()>this.saldo) {
				setSaldo(0);
				setSobregiroActual(this.sobregiroActual+(credito.getCuotaMensual()-this.saldo));
			}
			else{
				this.restarSaldo(credito.getCuotaMensual());
			}
			credito.setDeuda(credito.getDeuda()-credito.getCuotaMensual());
			Transaccion trans = new Transaccion(this,credito.getBanco().getNombreBanco(),credito.getCuotaMensual());
			historialTransferencia.add(trans);
			return true;
		}
		else {
			return false;
		}
	}
	
//	-------------------------------------- Metodos get-set --------------------------------------

    public static ArrayList<Corriente> getCorriente() { return corriente; }

    public static void setCorriente(ArrayList<Corriente> corriente) { Corriente.corriente = corriente; }
	
	public void setSobregiroActual(float sobregiroActual) { this.sobregiroActual = sobregiroActual; }

	public float getSobregiroActual() { return this.sobregiroActual; }		

	public ArrayList<Transaccion> getHistorialTransferencia(){ return this.historialTransferencia; }
	
	public void setHistorialTransferencia(ArrayList<Transaccion> historial){ this.historialTransferencia = historial; }

}

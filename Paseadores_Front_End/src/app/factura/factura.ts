import { Tarjeta } from '../tarjeta/tarjeta';
export class Factura{
  id: number;
  referencia: number;
  concepto: string;
  descuento: number;
  descripcionCompra: string;
  totalCompra: number;
  impuesto: number;
  subTotal: number;
  diasEstadia: number;
  tarjeta: Tarjeta;
  constructor(
    id: number,
    referencia: number,
    concepto: string,
    descuento: number,
    descripcionCompra: string,
    totalCompra: number,
    impuesto: number,
    subTotal: number,
    diasEstadia: number,
    tarjeta: Tarjeta
  ){
    this.id = id;
    this.referencia =  referencia;
    this.concepto = concepto;
    this.descuento = descuento;
    this.descripcionCompra = descripcionCompra;
    this.totalCompra = totalCompra;
    this.impuesto = impuesto;
    this.subTotal = subTotal;
    this.diasEstadia = diasEstadia;
    this.tarjeta = tarjeta;
  }
}

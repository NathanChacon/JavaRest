package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/echo")
public class SalarioLiquido {
	
	@GET
	@Produces("text/plain")
	public String calcular(@QueryParam("salarioBruto") Float salarioBruto,@QueryParam("pensaoAlimenticia") Float pensaoAlimenticia,@QueryParam("numeroDependentes") int numeroDependentes) {
		float taxaInss = this.getTaxaInss(salarioBruto);
		float baseCalculoIrrf = this.getBaseCalculoIrrf(salarioBruto,taxaInss,pensaoAlimenticia,numeroDependentes);
		float taxaIrrf = this.getTaxaIrrf(baseCalculoIrrf);
		float totalTaxas = taxaInss + taxaIrrf;
		float salarioLiquido = salarioBruto - totalTaxas;
		String salarioLiquidoString = Float. toString(salarioLiquido);
		return salarioLiquidoString;
	}
	
	private Float getTaxaInss(Float salario) {
		float taxa = 0f;
		if(salario <= 1045.00) {
			taxa = ((salario * 7.5f)/100);
		}
		else if(salario >= 1045.01 && salario <= 2089.60) {
			taxa = ((salario * 9)/100);
		}
		else if(salario >= 2080.61 && salario <= 3134.40) {
			taxa = ((salario * 12)/100);
		}
		else if(salario >= 3134.41 && salario <= 6101.06) {
			taxa = ((salario * 14)/100);
		}
		else if(salario > 6101.06) {
			taxa = 671.12f;
		}
		
		return taxa;
	}
	
	private Float getBaseCalculoIrrf(Float salarioBruto, Float taxaInss, Float pensaoAlimenticia, int numeroDependentes) {
		float salarioMenosInss = salarioBruto - taxaInss;
		float baseCalculo = ((salarioMenosInss - pensaoAlimenticia) - (numeroDependentes * 189.59f));
		
		return baseCalculo;
	}
	
	private Float getTaxaIrrf(Float baseCalculo) {
		float taxaIrrf = 0f;
		
		if(baseCalculo <= 1903.98) {
			taxaIrrf = 0f;
		}
		else if(baseCalculo >= 1903.99 && baseCalculo <= 2826.65) {
			taxaIrrf = ((baseCalculo * 7.5f)/100) - 142.80f;
		}
		else if(baseCalculo >= 2826.66 && baseCalculo <= 3751.05) {
			taxaIrrf = ((baseCalculo * 15.0f)/100) - 354.80f;
		}
		else if(baseCalculo >= 3751.06 && baseCalculo <= 4664.68) {
			taxaIrrf = ((baseCalculo * 22.5f)/100) - 636.13f;
		}
		else if(baseCalculo > 4664.68) {
			taxaIrrf = ((baseCalculo * 27.5f)/100) - 869.36f;
		}
		
		return taxaIrrf;
	}

}

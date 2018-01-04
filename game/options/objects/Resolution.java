package options.objects;

import engine.options.objects.OptionDropBox;

public class Resolution extends OptionDropBox {

	private double[] factors;

	public Resolution(String[] values) {
		super(values);
		factors = new double[values.length];
		for(int i = 0; i < values.length; i++) {
			factors[i] = 1 - (0.2 *i);
		}
	}

	public int getResFactor() {
		return super.getValue();
	}

}

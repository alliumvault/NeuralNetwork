package network.activationfunctions;

public interface ActivationFunction {
    public static Boolean Boolean = new Boolean();
    public static Identity Identity = new Identity();
    public static Sigmoid ActivationSigmoid = new Sigmoid();
    public static HyperbolicTangent HyperbolicTangent = new HyperbolicTangent();
    public static ReLu ReLu = new ReLu();

    public float activation(float input);

    public float derivative(float input);
}

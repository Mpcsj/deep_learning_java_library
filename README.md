# deep_learning_java_library


<h2>Creating a simple classification Model:</h2>
```
 public static void trab5IC(){
        int indexClasses[] = new int[]{0}; // array with the output indexes in csv file dataframe\
        // library for handling CSV files\
        // the first attribute defines the test size proportion\
        // the second defines the csv file\
        // the third is the output indexes\
        IDados dados = new DadosFromCSV(0.3, "base_trab2.csv", indexClasses);\
        // number of inputs\
        int nEntradas = dados.getBaseEntradaTreino().get(0).length;\
        double threshold = 0.5;\
        // NeuralNetwork interface used for classification and Refression model\
        IRedeNeural nn = new NN_Classifier(nEntradas, new SimpleThreshold(threshold));// input layer\
        nn.addCamada(10, new NeuronioComRelu()); // first hidden layer with 10 neurons and all of them uses Relu neuron\
        nn.addCamada(dados.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(0.0001));// output layer(using sigmoid      //function)\
        // getting historial data about the training process\
        IHistory historicos[] = nn.treinaRede(dados.getBaseEntradaTeste(), dados.getBaseSaidaTreino(),\
                dados.getBaseEntradaTeste(), dados.getBaseSaidaTeste(), 10000, 1, LossMetrics.ModuleDif);\

    }

```

# deep_learning_java_library
<<<<<<< HEAD
# deep_learning_java_library
=======

<h2>Creating a simple classification Model:</h2>
 <b>public static void trab5IC(){</b><br/>
        int indexClasses[] = new int[]{0}; // array with the output indexes in csv file dataframe  <br/>
        // library for handling CSV files  <br/>
        // the first attribute defines the test size proportion  <br/>
        // the second defines the csv file<br/>
        // the third is the output indexes<br/>
        IDados dados = new DadosFromCSV(0.3, "base_trab2.csv", indexClasses);<br/>
        // number of inputs<br/>
        int nEntradas = dados.getBaseEntradaTreino().get(0).length;<br/>
        double threshold = 0.5;<br/>
        // NeuralNetwork interface used for classification and Refression model<br/>
        IRedeNeural nn = new NN_Classifier(nEntradas, new SimpleThreshold(threshold));// input layer<br/>
        nn.addCamada(10, new NeuronioComRelu()); // first hidden layer with 10 neurons and all of them uses Relu neuron<br/>
        nn.addCamada(dados.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(0.0001));// output layer(using sigmoid      //function)<br/>
        // getting historial data about the training process<br/>
        IHistory historicos[] = nn.treinaRede(dados.getBaseEntradaTeste(), dados.getBaseSaidaTreino(),<br/>
                dados.getBaseEntradaTeste(), dados.getBaseSaidaTeste(), 10000, 1, LossMetrics.ModuleDif);<br/>

   <b> }</b>
>>>>>>> d405606be3065008706b46cc196a940bf85f58c6

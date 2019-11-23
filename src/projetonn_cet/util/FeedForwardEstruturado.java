/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mpcsj
 */
public class FeedForwardEstruturado {

    public static double[] preencheEntrada(String resistenciaIdeal, Agregado agregados, String cimento, String solucao, int qtdDias) {
        double entrada[] = new double[14];
        entrada[0] = Double.parseDouble(resistenciaIdeal);
        entrada[1] = agregados.Al2O3;
        entrada[2] = agregados.CaO;
        entrada[3] = agregados.Fe2O3;
        entrada[4] = agregados.K2O;
        entrada[5] = agregados.MgO;
        entrada[6] = agregados.P2O5;
        entrada[7] = agregados.SiO2;
        entrada[8] = agregados.TiO2;
        // tratando  o cimento
        switch (cimento) {
            case "CPII":
                entrada[9] = 1;
                entrada[10] = 0;
                entrada[11] = 0;
                break;
            case "CPIII":
                entrada[9] = 0;
                entrada[10] = 1;
                entrada[11] = 0;
                break;
            case "CPIV":
                entrada[9] = 0;
                entrada[10] = 0;
                entrada[11] = 1;
                break;
        }
        entrada[12] = Double.parseDouble(solucao);
        entrada[13] = qtdDias;

        return entrada;
    }

    public static double[] normalizaOuDesnormaliza(double entrada[], boolean parte1) {
        if (parte1) {
            // se estou normalizando a entrada
            //Lista de menores elementos de cada coluna:
            double maiores[] = {50.0, 14.76, 50.18, 4.18, 5.47, 9.5, 0.3, 67.83, 0.71, 1.0, 1.0, 1.0, 50.0, 180.0};
            double menores[] = {20.0, 1.24, 2.08, 0.51, 0.32, 0.72, 0.02, 3.8, 0.04, 0.0, 0.0, 0.0, 0.0, 28.0};

            for (int i = 0; i < entrada.length; i++) {
                entrada[i] = (entrada[i] - menores[i]) / (maiores[i] - menores[i]);
            }
        } else {
            //se estou desnormalizando a saida
            double maiores[] = {22.8, 65.0, 61.9};
            double menores[] = {17.16, 20.0, 14.5};
            for (int i = 0; i < entrada.length; i++) {
                entrada[i] = entrada[i] * (maiores[i] - menores[i]) + menores[i];
            }
        }
        return entrada; // nao precisa, so me da uma sensacao maior do significado de uma funcao quando eu uso retorno
    }

    public static List<double[][]> retornaListaRede1() {
        List<double[][]> res = new ArrayList<>(2);
        double a[][] = {{1.9129739066919167, 0.9067196309047252, -0.03121346149359306, 1.9830742457543349, 0.1388577967570241, -0.742292117842743, 1.4058048001960408, -2.06684181690005}, {0.41645102846221876, 0.17625847995099508, 0.005202085616030186, 0.14864492513215738, -0.055567563921926084, -0.2653054870673239, 0.16948988949016253, 0.29329850043437183}, {0.8976061369580403, -0.9736509462739141, 1.2156073727116665, 0.6869542963367966, 0.05602432189997224, 1.8742460929210336, -0.6860625922370065, 1.667059610461965}, {0.044595040415324694, -0.03735234507144388, 0.6542203278033176, -0.0408218824956969, -0.27449484185087636, 0.6766196547943643, -0.37107810287165405, 0.3964826632913455}, {-0.25636404339830837, 0.22524272988091662, 0.26815114368073784, 0.4804059358531966, 0.10706992815965963, 0.22080407695742008, 0.24328260881594815, -0.16548394225848195}, {0.816238171948276, -0.9061532699150305, 0.4152133020565654, 1.8846673303384678, -0.49130708065360784, -0.9507056905760256, 3.489897765924432, 0.5493304143819709}, {0.26844715958093807, -0.3782933371046262, 0.42264009599749575, 0.9711280611286393, 0.8421637579565652, 2.282092512240601, 0.22579587606491772, 0.9102189975275644}, {0.0707333227137966, -0.7456720659866466, 0.32582136360090325, 0.780131875957524, 0.5053277062796444, -0.4736633516499784, 0.6041944914027234, 0.09248398708898517}, {-0.12588322147704045, -0.3417383817551478, 0.7741765203211523, -0.16003888626517365, -0.02138701870890609, 0.09655567274739091, -0.10968078071219474, -0.14312011725250948}, {0.28200557379994046, -2.1720699945941266, 0.6393618420185778, -1.6293144091166414, 0.38667495112876893, 1.6503978180226027, 1.273111786435321, 0.7212254142144001}, {1.0076094439117598, 2.5150384299199424, 0.4203383297487132, -0.011187463095303697, -0.35683515135783006, -1.7303243555039745, -0.768139161873107, 0.7238066970661469}, {-0.14631898703007487, -1.453516366083458, 0.23453800403307334, 2.9370173996690645, 0.8076439189235639, 1.674868044184164, -0.04838416538347687, 0.6175212782668745}, {-1.4053055185057801, 2.443036352239262, 0.49904777193130895, -2.428502786362666, 1.8347972747842345, -4.1604372397636356, -0.49687251684538103, -0.20826195952251855}, {-0.4736825898484067, -2.0324986871516506, 0.4967960780959932, 2.5962033448538917, -0.11917322874517558, 0.9999551592866148, 2.6051982518972876, 5.938917068354522}};
        double b[][] = {{-4.542444345517381}, {4.167869856850467}, {0.2095390888725598}, {-2.4022382791998154}, {1.9968009668898077}, {3.8585132153277715}, {3.6380072390477527}, {-3.7235519137186035}};
        res.add(a);
        res.add(b);
        return res;
    }

    public static List<double[][]> retornaListaRede2() {
        List<double[][]> res = new ArrayList<>(2);
        double a[][] = {{3.2689674785862737, 0.9289390423551006, 1.1126912775936675, 0.14405503204066306, 0.2722632965363997, 0.37976793192014763, 0.9200596147760332, -1.8402260843413796}, {-0.20749596851343513, 0.16465055886011484, 0.5020380299019617, 0.7505196235287713, 0.21995815153449957, 0.09003627315557398, 0.9059682603901504, 0.7059221068341681}, {-3.5955477819399193, 0.12575289243492258, 0.018103487467283314, 0.7997445568540694, 0.7763832220409997, 0.8486407067697528, 0.8276732865731548, 2.6849743414394456}, {0.43921729070820786, 0.13624261273542373, 0.5380556999904552, 0.7327953656887554, 0.5978887182452919, 0.09342635942645315, 0.2570889733716168, 0.204539297733397}, {-0.45484275775061167, 0.6426564512038165, 0.37396435537302, 0.9115985505337318, 0.8792732746978641, 0.21941185502185373, 0.20045232619339676, 0.8072515253464321}, {-0.05445181314372726, 0.8043920976255824, -1.285809927492571, -0.1161715471216844, -0.10579073804020794, 0.6474574588817106, 0.3862589221172095, 2.3301712487727584}, {-1.7018717328296877, 0.5154393279678576, -0.11695808263569514, 0.44719866298698124, 1.1347173583953634, 0.6715734187838387, 0.06742238700172189, 1.5847491545466528}, {-0.46946619256346395, 0.9315224563623804, -0.4543719306260113, 0.6455923830770657, 0.529408478184879, 0.2902743896449162, 0.08915610966127904, 0.4867284296597806}, {0.04464805405200336, 0.7928859544405856, -0.09848851511806704, 0.3210747795091363, 0.668705768357088, 0.2528360531842195, 0.413716265978848, 0.932727731661096}, {-0.6149235557116328, 0.9870458804626715, -0.1846197355718378, 0.007658621934814103, 1.032175413926583, 0.8431517316304258, 0.5106047961849752, 2.2673933055864737}, {-1.3949846955565541, 0.4330357020193187, 1.723716827404284, 1.1710263304035036, -0.004250565647565008, 0.3786383152806397, 0.821401714382774, 1.7985552053729097}, {-0.9610479368587914, 0.7047452182379255, -0.8823228514647531, -0.24481227479970027, 0.8555859601864964, 0.3171530062065156, 0.8503307890465135, -0.11478411022080635}, {-0.3394438124578186, 0.5131387984066226, -0.331997684866926, 0.9301378508431914, -0.27558228014820624, 0.33613619097888076, 0.15459621457105374, -0.7790761996072404}, {0.5613508631454689, 0.36104442995812147, -0.5066660493443963, 0.4317650915528913, 0.3679365696716382, -0.06401164500131952, 0.7627999486620258, -2.082075893084407}};
        double b[][] = {{3.973565210005976}, {-0.3632463146796003}, {3.2627245363220565}, {-1.073628561122624}, {0.9746991803092825}, {0.2614289524635731}, {-0.022004985139784463}, {-3.317301998094188}};
        res.add(a);
        res.add(b);
        return res;
    }

    public static List<double[][]> retornaListaRede3() {
        List<double[][]> res = new ArrayList<>(2);
        double a[][] = {{-1.2760474785091493, 1.49816220833908, 2.6759773854989453, 0.7327798939343869, -0.9238209069395994, 0.30363780638040544, 0.5462531023594175, 0.516292667404925}, {0.07826313986162427, 0.3802067131536023, 0.45403695981678055, 0.03034639241810286, 0.4677705227914249, 1.0527166468860112, 0.7992401906324108, 0.8503882065451271}, {0.6377625851509066, -0.3348792560648066, 0.4579401217801637, 1.1521283548090149, -1.5553617103345585, -0.8704266695859125, 0.34075724460472095, 1.0519076358888781}, {0.842677083058594, 0.07707241488723221, -0.44472174508526, 0.7840145266394394, 0.2813503096438978, 1.2962710323592814, 0.42299764015745955, 0.6170497086911636}, {0.8198837180472786, 0.04985949065892849, 0.43682231470001576, 0.9893755201421038, 0.33299417602038783, 1.0980764115716255, 0.3457386593740548, 0.37544158778663483}, {1.289536348991497, 0.010532220826810797, 1.356476520120131, 0.1374956420064615, 0.9528303861534216, 0.11270379596078868, -0.16949582089158294, 0.49761998752278863}, {0.9266286385860207, 0.39112649337273103, 1.9910152802223149, 0.5488474915234034, -1.0519749338144464, 1.470519779968135, 1.0518742692592793, 0.5710598675683505}, {0.42772510130650215, 0.4201839779091687, 0.1330896868547713, 0.26629763881567503, 0.13619693188952037, 1.3865127305807448, 0.013902830307401125, 0.28699935293792117}, {-0.05665295112429077, 0.23876609195060358, 0.32870555924426453, 0.5391309391317025, 0.4117788436375865, 1.2591438190128725, 0.05226042383491989, -0.07964221817473761}, {-0.6553388774883042, 0.8883568830969072, 2.1531190508561986, 0.22560128534781615, 0.19479672537899764, -1.2321290645890925, 0.738348278402499, 0.9628621362705624}, {1.653507344722673, 0.022820943938539104, 2.1938245927307647, 0.4531134850462921, -1.0137318719134973, 0.223315919918004, 0.796528290823728, 0.8327456165783074}, {0.2245470447081272, -1.126226422413144, -2.7945260365817557, 0.2995813102829668, 0.09138164101714064, 1.6662503876003432, 0.7258878701105949, 0.10045175955199494}, {0.5997874239762874, -1.352049491871422, 8.438674531205852, 0.18438222602866558, 1.198293879295993, -4.602976699533945, -0.6358985564850711, 0.8408886523138653}, {-0.9721521231994409, 1.3823107087510198, -0.41779522544421827, 0.7262855677397277, -0.19451122690469888, 1.739238663374523, 0.8633958929587094, 0.5237444343635868}};
        double b[][] = {{2.26226476736252}, {3.002094919914443}, {-7.4069905770000375}, {1.1327322503694082}, {2.579337643122114}, {-3.4042692652826205}, {1.7355052414395546}, {0.6528358394729894}};

        res.add(a);
        res.add(b);
        return res;
    }

//    public static double feedForward(List<double[][]> pesosRede, double entrada[]) {
//        double[][] a = {entrada};
//        for (double[][] matrizPesosDaCamada : pesosRede) {
//            a = multiplicaMatrizes(a, matrizPesosDaCamada);
//            // depois de multiplicar as matrizes, aplico a funcao de ativacao em cada valor
//            for (int i = 0; i < a.length; i++) {
//                a[0][i] = 1 / (1 + Math.exp(-1 * a[0][i])); // funcao sigmoidal
//            }
//        }
//        // no final do processo, resultara em apenas um valor
//        return a[0][0];
//    }
    public static double sigmoid(double a) {
        return 1 / (1 + Math.exp(-a));
    }

    public static double feedForward(List<double[][]> pesosRede,double[] entrada) { // recebe apenas uma tupla de entrada
        // primeiro faz o produto escalar entre a matriz de peso e a matriz de entrada,
        // depois, usa a funcao de ativacao apropriada para o neuronio
        // o resultado de cada operacao, será mandado para cada neuronio da lista de neuronios da camada
        // onde, com base no que foi decidido no construtor, cada neuronio usará a função de ativação previamente selecionada

        double[][] pesos = pesosRede.get(0);

        double[] saida1 = new double[8]; // Java já cria um vetor com zero em todas as posicoes
        for (int j = 0; j < saida1.length; j++) {
            for (int i = 0; i < entrada.length; i++) {
                saida1[j] += entrada[i] * pesos[i][j]; // conferir
            }
            saida1[j] = sigmoid(saida1[j]);
        }

        double saida2 = 0;
        pesos = pesosRede.get(1);
        for (int i = 0; i < saida1.length; i++) {
            saida2 += saida1[i] * pesos[i][0]; // conferir
        }
        saida2 = sigmoid(saida2);
        return saida2;
    }

    public static double[][] multiplicaMatrizes(double[][] mat1, double[][] mat2) {
        double[][] res = new double[mat1.length][mat2[0].length];
        int i, j, k;
        int aux, proxLinha, proxColuna;
        proxLinha = proxColuna = 0;
        for (i = 0; i < mat1.length; i++) {// varrendo por linhas da primeira
            for (k = 0; k < mat2[0].length; k++) {
                aux = 0;
                for (j = 0; j < mat2.length; j++) {// varrendo por colunas da segunda
                    aux += mat1[i][j] * mat2[j][k];
                }
                if (proxColuna == mat2[0].length) {
                    proxColuna = 0;
                    proxLinha++;
                }
                res[proxLinha][proxColuna] = aux;
                proxColuna++;
            }
        }
        return res;
    }

    // funcao principal, que recebe as entradas da interface
    public static double[] calcula(String resistenciaIdeal, Agregado agregados, String cimento, String solucao, int qtdDias) {
        double entrada[] = preencheEntrada(resistenciaIdeal, agregados, cimento, solucao, qtdDias);
        double res[] = new double[3];
        entrada = normalizaOuDesnormaliza(entrada, true);
        // cada um dos valores de saida da tabela
        res[0] = feedForward(retornaListaRede1(), entrada);
        res[1] = feedForward(retornaListaRede2(), entrada);
        res[2] = feedForward(retornaListaRede3(), entrada);
        normalizaOuDesnormaliza(res, false);
        return res;
    }
}
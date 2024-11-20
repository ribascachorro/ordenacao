import java.io.FileWriter;
import java.io.IOException;

public class OrdenaAí {

    // minimo entre dois inteiros
    private static int minimo(int a, int b) {
        return (a < b) ? a : b;
    }

    // Método de Merge Sort Iterativo
    public static Metricas mergeSortIterativo(int[] vetor, int tamanho) {
        Metricas metricas = new Metricas();
        int[] temp = new int[tamanho];

        long inicio = System.nanoTime();

        for (int largura = 1; largura < tamanho; largura *= 2) {
            for (int inicioV = 0; inicioV < tamanho; inicioV += 2 * largura) {
                int esquerda = inicioV;
                int meio = minimo(inicioV + largura, tamanho);
                int direita = minimo(inicioV + 2 * largura, tamanho);
                mescla(vetor, temp, esquerda, meio, direita, metricas);
                metricas.contaIteracao++;
            }
        }

        long fim = System.nanoTime();
        metricas.tempoExecucao = fim - inicio;
        return metricas;
    }

    private static void mescla(int[] vetor, int[] temp, int esquerda, int meio, int direita, Metricas metricas) {
        int i = esquerda;
        int j = meio;
        int k = esquerda;

        while (i < meio && j < direita) {
            if (vetor[i] <= vetor[j]) {
                temp[k++] = vetor[i++];
            } else {
                temp[k++] = vetor[j++];
                metricas.contaTroca++;
            }
            metricas.contaIteracao++;
        }

        while (i < meio) {
            temp[k++] = vetor[i++];
            metricas.contaIteracao++;
        }

        while (j < direita) {
            temp[k++] = vetor[j++];
            metricas.contaTroca++;
            metricas.contaIteracao++;
        }

        for (i = esquerda; i < direita; i++) {
            vetor[i] = temp[i];
            metricas.contaTroca++;
            metricas.contaIteracao++;
        }
    }

    // Metodo Radix Sort
    public static Metricas ordenaRadix(int[] vetor, int tamanho) {
        Metricas metricas = new Metricas();
        int max = vetor[0];

        // Encontrar o maior número
        for (int i = 1; i < tamanho; i++) {
            if (vetor[i] > max) {
                max = vetor[i];
            }
            metricas.contaIteracao++;
        }

        long inicio = System.nanoTime(); // Início da medição de tempo

        // Ordenar por cada dígito
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(vetor, exp, tamanho, metricas);
            metricas.contaIteracao++;
        }

        long fim = System.nanoTime(); // Fim da medição de tempo
        metricas.tempoExecucao = fim - inicio; // Tempo total do Radix Sort
        return metricas;
    }

    private static void countingSort(int[] vetor, int exp, int tamanho, Metricas metricas) {
        int[] saida = new int[tamanho];
        int[] contagem = new int[10];

        // Contar ocorrências
        for (int i = 0; i < tamanho; i++) {
            int digito = (vetor[i] / exp) % 10;
            contagem[digito]++;
            metricas.contaIteracao++;
        }

        // Acumular contagens
        for (int i = 1; i < 10; i++) {
            contagem[i] += contagem[i - 1];
            metricas.contaIteracao++;
        }

        // Construir o array de saída
        for (int i = tamanho - 1; i >= 0; i--) {
            int digito = (vetor[i] / exp) % 10;
            saida[contagem[digito] - 1] = vetor[i];
            contagem[digito]--;
            metricas.contaTroca++;
            metricas.contaIteracao++;
        }

        // Copiar para o array original
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = saida[i];
            metricas.contaTroca++;
            metricas.contaIteracao++;
        }
    }

    // Método  Quick Sort Iterativo
    public static Metricas quickSortIterativo(int[] vetor, int tamanho) {
        Metricas metricas = new Metricas();
        int[] pilha = new int[tamanho * 2]; // Aumentar o tamanho para evitar overflow

        // Inicializar a pilha
        int topo = -1;
        pilha[++topo] = 0;
        pilha[++topo] = tamanho - 1;

        long inicio = System.nanoTime();

        // Processar a pilha
        while (topo >= 0) {
            int alto = pilha[topo--];
            int baixo = pilha[topo--];

            int pivo = particiona(vetor, baixo, alto, metricas);
            metricas.contaIteracao++;

            if (pivo - 1 > baixo) {
                pilha[++topo] = baixo;
                pilha[++topo] = pivo - 1;
            }

            if (pivo + 1 < alto) {
                pilha[++topo] = pivo + 1;
                pilha[++topo] = alto;
            }
        }

        long fim = System.nanoTime();
        metricas.tempoExecucao = fim - inicio;
        return metricas;
    }

    private static int particiona(int[] vetor, int baixo, int alto, Metricas metricas) {
        int pivo = vetor[alto];
        int i = baixo - 1;
        for (int j = baixo; j < alto; j++) {
            metricas.contaIteracao++;
            if (vetor[j] < pivo) {
                i++;
                // Trocar vetor[i] e vetor[j]
                int temp = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temp;
                metricas.contaTroca++;
            }
        }
        // Trocar vetor[i + 1] e vetor[alto]
        int temp = vetor[i + 1];
        vetor[i + 1] = vetor[alto];
        vetor[alto] = temp;
        metricas.contaTroca++;
        return i + 1;
    }

    //  copiar arrays
    private static int[] copiaVetor(int[] original, int tamanho) {
        int[] copia = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // verificar se o array está ordenado
    private static boolean estaOrdenado(int[] vetor, int tamanho) {
        for (int i = 0; i < tamanho - 1; i++) {
            if (vetor[i] > vetor[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // mapear identificadores numéricos para nomes dos algoritmos
    private static String mapAlgoritmo(int id) {
        switch (id) {
            case 1:
                return "Merge Sort Iterativo";
            case 2:
                return "Radix Sort";
            case 3:
                return "Quick Sort Iterativo";
            default:
                return "Desconhecido";
        }
    }


    private static void exportaParaCSV(Metricas[][] tabelaMetricas, int[] nomesDataSets, int[] nomesSorts) {
        try {
            FileWriter csvWriter = new FileWriter("D:\\Documentos\\ordenagem\\Java\\metricas_sort.csv");


            csvWriter.append("DataSet,Algoritmo,TempoExecucao(ns),NumeroTrocas,NumeroIteracoes\n");

            for (int i = 0; i < tabelaMetricas.length; i++) {
                for (int j = 0; j < tabelaMetricas[i].length; j++) {
                    csvWriter.append("DataSet" + nomesDataSets[i]).append(",")
                            .append(mapAlgoritmo(nomesSorts[j])).append(",")
                            .append(Long.toString(tabelaMetricas[i][j].tempoExecucao)).append(",")
                            .append(Long.toString(tabelaMetricas[i][j].contaTroca)).append(",")
                            .append(Long.toString(tabelaMetricas[i][j].contaIteracao)).append("\n");
                }
            }

            csvWriter.flush();
            csvWriter.close();
            System.out.println("Métricas exportadas para 'metricas_sort.csv' com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        int[][] dataSets = {
                // DataSet1
                {1, 100, 2, 99, 3, 98, 4, 97, 5, 96, 6, 95, 7, 94, 8, 93, 9, 92, 10, 91, 11, 90, 12, 89, 13, 88, 14, 87, 15, 86, 16, 85, 17, 84, 18, 83, 19, 82, 20, 81, 21, 80, 22, 79, 23, 78, 24, 77, 25, 76},
                // DataSet2
                {1, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90,89,88,87,86,85,84,83,82,81,80,79,78,77,76,75,74,73,72,71,70,69,68,67,66,65,64,63,62,61,60,59,58,57,56,55,54,53,52},
                // DataSet3
                {50, 49, 48, 47, 46, 45, 44, 43 , 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5,4,3,2,1}
        };


        int[] nomesDataSets = {1, 2, 3}; // DataSet1, DataSet2, DataSet3
        int[] nomesSorts = {1, 2, 3}; // 1: Merge Sort Iterativo, 2: Radix Sort, 3: Quick Sort Iterativo

        // Tabela para armazenar métricas
        Metricas[][] tabelaMetricas = new Metricas[3][3];

        for (int i = 0; i < dataSets.length; i++) {
            int tamanho = dataSets[i].length;
            // Copiar o array para não modificar o original
            int[] paraMerge = copiaVetor(dataSets[i], tamanho);
            int[] paraRadix = copiaVetor(dataSets[i], tamanho);
            int[] paraQuick = copiaVetor(dataSets[i], tamanho);

            // Merge Sort
            tabelaMetricas[i][0] = mergeSortIterativo(paraMerge, tamanho);
            System.out.println("Merge Sort Iterativo executado no DataSet" + nomesDataSets[i]);
            if (estaOrdenado(paraMerge, tamanho)) {
                System.out.println("  Merge Sort Iterativo: Ordenação correta.");
            } else {
                System.out.println("  Merge Sort Iterativo: Ordenação incorreta.");
            }

            // Radix Sort
            tabelaMetricas[i][1] = ordenaRadix(paraRadix, tamanho);
            System.out.println("Radix Sort executado no DataSet" + nomesDataSets[i]);
            if (estaOrdenado(paraRadix, tamanho)) {
                System.out.println("  Radix Sort: Ordenação correta.");
            } else {
                System.out.println("  Radix Sort: Ordenação incorreta.");
            }

            // Quick Sort
            tabelaMetricas[i][2] = quickSortIterativo(paraQuick, tamanho);
            System.out.println("Quick Sort Iterativo executado no DataSet" + nomesDataSets[i]);
            if (estaOrdenado(paraQuick, tamanho)) {
                System.out.println("  Quick Sort Iterativo: Ordenação correta.");
            } else {
                System.out.println("  Quick Sort Iterativo: Ordenação incorreta.");
            }
        }


        exportaParaCSV(tabelaMetricas, nomesDataSets, nomesSorts);


        System.out.println("\nComparação de Algoritmos de Ordenação:");
        for (int i = 0; i < dataSets.length; i++) {
            System.out.println("\nDataSet" + nomesDataSets[i] + ":");
            for (int j = 0; j < nomesSorts.length; j++) {
                System.out.println("  " + mapAlgoritmo(nomesSorts[j]) + ":");
                System.out.println("    Tempo de Execução (ns): " + tabelaMetricas[i][j].tempoExecucao);
                System.out.println("    Número de Trocas: " + tabelaMetricas[i][j].contaTroca);
                System.out.println("    Número de Iterações: " + tabelaMetricas[i][j].contaIteracao);
            }
        }
    }
}

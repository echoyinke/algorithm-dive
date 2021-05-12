package com.keyin.opration.kmeans.src;/*
 * Programmed by Shephalika Shekhar
 * Class for Kmeans Clustering implemetation
 */

import java.io.IOException;
import java.util.*;

/**
 * 收敛条件：迭代次数，或者需要重新调整的样本数少到一定程度
 * 
 * 
 * K-means 有一个著名的解释：牧师—村民模型：
 * <p>
 * 有四个牧师去郊区布道，一开始牧师们随意选了几个布道点，并且把这几个布道点的情况公告给了郊区所有的村民，于是每个村民到离自己家最近的布道点去听课。
 * 听课之后，大家觉得距离太远了，于是每个牧师统计了一下自己的课上所有的村民的地址，搬到了所有地址的中心地带，并且在海报上更新了自己的布道点的位置。
 * 牧师每一次移动不可能离所有人都更近，有的人发现A牧师移动以后自己还不如去B牧师处听课更近，于是每个村民又去了离自己最近的布道点……
 * 就这样，牧师每个礼拜更新自己的位置，村民根据自己的情况选择布道点，最终稳定了下来。
 *所以 K-means 的算法步骤为：
 * 选择初始化的 k 个样本作为初始聚类中心 [公式] ；
 * 针对数据集中每个样本 [公式] 计算它到 k 个聚类中心的距离并将其分到距离最小的聚类中心所对应的类中；
 * 针对每个类别 [公式] ，重新计算它的聚类中心 [公式] （即属于该类的所有样本的质心）；
 * 重复上面 2 3 两步操作，直到达到某个中止条件（迭代次数、最小误差变化等）。
 */
public class K_Clusterer extends ReadDataset {

    public K_Clusterer() {
        // TODO Auto-generated constructor stub
    }

    //main method
    public static void main(String args[]) throws IOException {
        ReadDataset r1 = new ReadDataset();
        r1.features.clear();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the filename with path");
        String file = sc.next();
        r1.read(file); //load data
        int ex = 1;
        do {
            System.out.println("Enter the no. of clusters");
            int k = sc.nextInt();
            System.out.println("Enter maximum iterations");
            int max_iterations = sc.nextInt();
            System.out.println("Enter distance metric 1 or 2: \n1. Euclidean\n2. Manhattan");
            int distance = sc.nextInt();
            //Hashmap to store centroids with index
            Map<Integer, double[]> centroids = new HashMap<>();
            // calculating initial centroids
            double[] x1 = new double[numberOfFeatures];
            int r = 0;
            for (int i = 0; i < k; i++) {

                x1 = r1.features.get(r++);
                centroids.put(i, x1);

            }
            //Hashmap for finding cluster indexes
            Map<double[], Integer> clusters = new HashMap<>();
            clusters = kmeans(r1.features, distance, centroids, k);
            // initial cluster print
	/*	for (double[] key : clusters.keySet()) {
			for (int i = 0; i < key.length; i++) {
				System.out.print(key[i] + ", ");
			}
			System.out.print(clusters.get(key) + "\n");
		}
		*/
            double db[] = new double[numberOfFeatures];
            //reassigning to new clusters
            for (int i = 0; i < max_iterations; i++) {
                for (int j = 0; j < k; j++) {
                    List<double[]> list = new ArrayList<>();
                    for (double[] key : clusters.keySet()) {
                        if (clusters.get(key) == j) {
                            list.add(key);
//					for(int x=0;x<key.length;x++){
//						System.out.print(key[x]+", ");
//						}
//					System.out.println();
                        }
                    }
                    db = centroidCalculator(list);
                    centroids.put(j, db);

                }
                clusters.clear();
                clusters = kmeans(r1.features, distance, centroids, k);

            }

            //final cluster print
            System.out.println("\nFinal Clustering of Data");
            System.out.println("Feature1\tFeature2\tFeature3\tFeature4\tCluster");
            for (double[] key : clusters.keySet()) {
                for (int i = 0; i < key.length; i++) {
                    System.out.print(key[i] + "\t \t");
                }
                System.out.print(clusters.get(key) + "\n");
            }

            //Calculate WCSS
            double wcss = 0;

            for (int i = 0; i < k; i++) {
                double sse = 0;
                for (double[] key : clusters.keySet()) {
                    if (clusters.get(key) == i) {
                        sse += Math.pow(Distance.eucledianDistance(key, centroids.get(i)), 2);
                    }
                }
                wcss += sse;
            }
            String dis = "";
            if (distance == 1) {
                dis = "Euclidean";
            } else {
                dis = "Manhattan";
            }
            System.out.println("\n*********Programmed by Shephalika Shekhar************\n*********Results************\nDistance Metric: " + dis);
            System.out.println("Iterations: " + max_iterations);
            System.out.println("Number of Clusters: " + k);
            System.out.println("WCSS: " + wcss);
            System.out.println("Press 1 if you want to continue else press 0 to exit..");
            ex = sc.nextInt();
        } while (ex == 1);
    }

    //method to calculate centroids
    public static double[] centroidCalculator(List<double[]> a) {

        int count = 0;
        //double x[] = new double[ReadDataset.numberOfFeatures];
        double sum = 0.0;
        double[] centroids = new double[ReadDataset.numberOfFeatures];
        for (int i = 0; i < ReadDataset.numberOfFeatures; i++) {
            sum = 0.0;
            count = 0;
            for (double[] x : a) {
                count++;
                sum = sum + x[i];
            }
            centroids[i] = sum / count;
        }
        return centroids;

    }

    /**
     * 找到离自己
     */
    //method for putting features to clusters and reassignment of clusters.
    public static Map<double[], Integer> kmeans(List<double[]> features, int distance, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist = 0.0;
        for (double[] x : features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {
                if (distance == 1) {
                    dist = Distance.eucledianDistance(centroids.get(j), x);
                } else if (distance == 2) {
                    dist = Distance.manhattanDistance(centroids.get(j), x);
                }
                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            clusters.put(x, k1);
        }

        return clusters;

    }

}

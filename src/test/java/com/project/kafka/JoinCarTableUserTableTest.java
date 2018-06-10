package com.project.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

/**
 * Created by rayanegouda on 10/06/2018.
 */
public class JoinCarTableUserTableTest {
    private Properties buildProducerProperties(String bootstrapServers) {
        /*./bin/confluent start --- Démarre Kafka Confluent
        * ./bin/kafka-topics --list --zookeeper localhost:2181 --- Liste des topics
        * --- Obtenir la liste des consumers group*/

        Properties properties = new Properties();
        /*
        * Bootstrap Server: La configuration de bootstrap.servers est juste (comme son nom l'indique)
        * une configuration utilisée au démarrage; alors chaque client (producer,consumer) sera capable
        * de se connecter directement aux brokers lire / écrire des topics / partitions) même si ce broker
        * n'est pas dans la configuration de bootstrap.servers.
        * C'est à dire que si on a un cluster kafka avec 2 brokers dans le bootstrap server et qu'on ajoute
        * un broker 3 sans l'ajouter aux config bootstrap  les clients (producer,consumer) pourront y accéder
        * sans pour autant redemarrer le server grâce aux metadata du broker 1 et 2.
        * Maintenant si le broker 1 et 2 tombent  il va falloir redemarrer les clients avec la nouvelle config
        * du bootstrap server compose du broker 3.
        * */
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        /*Nombre de Threads maximum de l'application*/
        properties.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 3);

         /*Cette propriete identifie l'application Kafka Streams de façon unique dans le cluster.*/
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "simple-join-tables");

         /* ./bin/kafka-console-consumer \
        --bootstrap-server localhost:9092 \
        --topic __consumer_offsets\
        --from-beginning
         Info: (p75-78) Topic __consumer_offset : Quand un consommateur fait le poll il retourne l'offset qui n'a pas été lu
         par le groupe de consommateurs.Les consommateurs track leur position dans chaque partition et le sauvegarde
         le topic __consumer_offset.
         Si le commited_offset toujours < à l'offset du dernier message alors le message pourrait être lu
         deux fois entre les deux offsets.Si c'est > les messages sont perdus


         Gestion deux methodes:
         -------Automatic Commit : Si lors de l'interval du commit et du rebalancing il ya production de nouveaux messages
          on risque d'avoir des doublons. (chose inevitable avec ce mode) .Ce mode ne donne pas la possibilité au dev de gérer
          les doublons  (enable.auto.commit=true)

         -------Commit Current Offset   (enable.auto.commit=false):
                 -----Sychronous Commit:
                 -----Asynchronous Commit:
                  */
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 5 * 1000);
         /**/
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
         /**/
        properties.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
         /**/
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
         /**/
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "1");
         /**/
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        return properties;
    }
}

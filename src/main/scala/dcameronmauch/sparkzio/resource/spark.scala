package dcameronmauch.sparkzio.resource

import org.apache.spark.sql.SparkSession
import zio.{Has, Task, ULayer, ZLayer}

object spark {
  type Spark = Has[Spark.Service]

  object Spark {
    trait Service {
      val sparkSession: Task[SparkSession]
    }

    val live: Service = new Service {
      val sparkSession: Task[SparkSession] = Task {
        SparkSession
          .builder()
          .appName("Spark ZIO")
          .master("local[*]")
          .getOrCreate()
      }
    }
  }

  val live: ULayer[Spark] = ZLayer.succeed(Spark.live)
}

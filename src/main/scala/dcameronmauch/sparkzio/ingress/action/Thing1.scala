package dcameronmauch.sparkzio.ingress.action

import dcameronmauch.sparkzio.common.Type.AppZIO
import dcameronmauch.sparkzio.resource.config.Config
import dcameronmauch.sparkzio.resource.shellEnv.ShellEnv
import dcameronmauch.sparkzio.resource.spark.Spark
import org.apache.spark.sql.{DataFrame, SparkSession}
import zio.{Task, ZIO}

object Thing1 {
  def apply(): AppZIO = ZIO.accessM { resource =>
    for {
      reference <- resource.get[Config.Service].reference
      snapshot <- resource.get[Config.Service].snapshot
      dbHost <- resource.get[ShellEnv.Service].dbHost
      dbLogin <- resource.get[ShellEnv.Service].dbLogin
      dbPassword <- resource.get[ShellEnv.Service].dbPassword
      sparkSession <- resource.get[Spark.Service].sparkSession
      leftDF <- readLeftDF(sparkSession)
      rightDF <- readRightDF(sparkSession)
      joinedDF <- joinDFs(leftDF, rightDF)
      _ <- writeDF(joinedDF)
    } yield ()
  }

  private def readLeftDF(sparkSession: SparkSession): Task[DataFrame] = Task {
    import sparkSession.implicits._
    Seq((1, "abc"), (2, "def"), (3, "ghi")).toDF("id", "foo")
  }

  private def readRightDF(sparkSession: SparkSession): Task[DataFrame] = Task {
    import sparkSession.implicits._
    Seq((1, 1.23), (2, 2.34), (3, 3.45)).toDF("id", "bar")
  }

  private def joinDFs(leftDF: DataFrame, rightDF: DataFrame): Task[DataFrame] = Task {
    leftDF.join(rightDF, Seq("id"))
  }

  private def writeDF(joinedDF: DataFrame): Task[Unit] = Task {
    joinedDF.printSchema()
    joinedDF.show(3, false)
  }
}

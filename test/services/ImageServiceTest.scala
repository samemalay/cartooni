package services

import java.io.File

import com.typesafe.config.ConfigFactory
import javax.imageio.ImageIO
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.{Configuration, Logger}

import scala.concurrent.ExecutionContext
import scala.io.Source
import scala.util.{Success, Try}

class ImageServiceTest  extends PlaySpec {

  private val log = Logger(this.getClass)
  implicit val dirPath = "test/resources"

  val myConfig = ConfigFactory.load("test.conf")
  val myConfiguration = new Configuration(myConfig)

  implicit val ec = ExecutionContext.global
  val service = new ImageService(myConfiguration)

  "Given picture" must {
    "convert to expected response" in {
      val convertedPix = service.convert("3M.png")
      val img = ImageIO.read(new File("test/resources/converted-3M.png"))
      convertedPix mustBe true
      (img.getWidth, img.getHeight) mustBe (2396, 1080)
    }
  }

  "List of files" must {
    "contain proper filename" in {
      val lst = service.listFiles
      lst.size mustBe 1
      lst(0) mustBe "3M.png"
    }
  }

}

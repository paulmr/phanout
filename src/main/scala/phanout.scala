package gu.com.phanout

import org.scalajs.dom.experimental.URL
import scala.scalajs.js.URIUtils.encodeURIComponent
import org.scalajs.dom._

object Phanout {

  val fakeJson = """{"pageviews":84049,"facebookPosts":[{"guardianUrl":"https://www.theguardian.com/food/2018/nov/28/you-need-to-prepare-yourselves-how-a-good-review-can-destroy-a-restaurant?utm_term=Autofeed&CMP=fb_us&utm_medium=Social&utm_source=Facebook#Echobox=1543413749","account":{"id":"456740217686384","pageId":"456740217686384","name":"GuardianUs","trackingCode":"fb_us","productionOffice":"US"},"facebookUrl":"https://facebook.com/456740217686384_2521507667876285","pictureUrl":"https://external.xx.fbcdn.net/safe_image.php?d=AQBVc4oW4SdqStYi&w=130&h=130&url=https%3A%2F%2Fechobox-media.s3.amazonaws.com%2Fsocial_media_images%2F469%2F469-1543413749-foj6htffam9dt9k3eq543h4625q0l7rb.jpg&cfs=1&_nc_hash=AQDxTQvcyDFBVOlm","path":"/food/2018/nov/28/you-need-to-prepare-yourselves-how-a-good-review-can-destroy-a-restaurant","tracked":true,"createdOn":"2018-11-28T14:13:53Z","message":"A US food critic has revealed how his power list of burgers led to the closure of the restaurant at No 1. It’s an experience his fellow critics can relate to","fCommentCount":0,"reach":0,"organicLikes":0,"organicShares":29,"facebookLikes":1,"facebookShares":0,"facebookReactions":1,"facebookReactionCounts":{"like":1,"love":0,"wow":0,"haha":0,"sad":0,"angry":0,"thankful":0}}]}"""

  val verbose = true

  val ophanBaseUrl = "https://dashboard.local.dev-gutools.co.uk"
  // val ophanBaseUrl = "http://localhost:9000"

  def makeOphanUrl(path: String) =
    s"$ophanBaseUrl/info"

  private def log(s: String) = if(verbose) println(s"[PMR] $s")

  def ophanLookup(url: String) = {
    val path = new URL(url).pathname
    val xhr = new XMLHttpRequest()
    val urlToLookup = makeOphanUrl(path)
    xhr.open("GET", urlToLookup)
    xhr.onload = { _ =>
      log(s"result: ${xhr.status}")
    }
    xhr.onerror = { e => log(s"unknown error: ${e.message}") }
    log(s"sending request to [$urlToLookup]")
    xhr.send()
  }

  def getGuardianLinks() = {
    val nodes =
      nodeListIterator(
        document.querySelectorAll(
          Seq(
            """a[href^="https://www.theguardian.com"]""",
            """a[href^="https://www.guardian.co.uk"]""",
            """a[href^="http://www.theguardian.com"]""",
            """a[href^="http://www.guardian.co.uk"]"""
          ).mkString(",")
        )
      )
    (nodes.collect {
      case a: html.Anchor => a
    }).toList
  }

  def nodeListIterator(nodes: NodeList) = new Iterator[Node] {
    private var index = 0
    def hasNext = index < nodes.length
    def next() = {
      val res = nodes(index)
      index = index + 1
      res
    }
  }

  def doOphan() = {
    val links = getGuardianLinks()
    /* now for each guardian link, we want to lookup the URL in Ophan */
    log(s"found ${links.length} links")
    for(l <- links.take(1)) {
      ophanLookup(l.href)
    }
  }

  def main(args: Array[String]): Unit = {
    log(s"setting onload event [changed] ${document.readyState}")
    doOphan()
    // else document.addEventListener("DOMContentLoaded", (_: Event) => {
    //   log("onload() fired")
    //   doOphan()
    // })
  }
}

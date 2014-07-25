import ratpack.codahale.metrics.CodaHaleMetricsModule
import ratpack.groovy.markuptemplates.MarkupTemplatingModule
import ratpack.launch.LaunchConfig

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {

  def pixel

  bindings {
    add new CodaHaleMetricsModule().jvmMetrics().jmx().websocket().healthChecks()
    add new MarkupTemplatingModule()
    init { LaunchConfig config ->
      pixel = config.getOther('pixel.location', '')
    }
  }

  handlers {

      def names = ["Aphrodite", "Apollo", "Ares", "Artemis", "Athena", "Demeter", "Dionysus", "Hades", "Pluto", "Hephaestus", "Hera", "Hermes", "Hestia", "Poseidon", "Zeus"]

      handler {
          response.headers.with {
            set("Cache-Control", "no-cache, no-store, must-revalidate")
            set("Pragma", "no-cache")
            set("Expires", "0")
          }

          def map = request.headers.names.sort().inject([:]) { map, key -> map[key] = request.headers.getAll(key); map }

          Collections.shuffle(names, new Random())

          render groovyMarkupTemplate('index.gtpl',
                                          headers: map,
                                          method: request.method.name,
                                          path: request.path,
                                          query: request.query,
                                          names: names,
                                          pixel: pixel)
      }
  }
 }

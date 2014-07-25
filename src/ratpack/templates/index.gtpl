yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta(charset:'utf-8')
        title(title ?: 'Dynamic Site')
        meta('http-equiv': "Content-Type", content:"text/html; charset=utf-8")
        meta(name: 'viewport', content: 'width=device-width, initial-scale=1.0')
    }
    body {
        h2 ('Request Info')
        dl {
            dt('Method')
            dd { pre(method) }
            dt('Path')
            dd { pre(path) }
            dt('Query')
            dd { pre(query) }
            dt('Headers')
            headers.keySet().each { key ->
            dd {
                pre { yield "${key} - ${headers[key].join(', ')}" }
            }
            }
        }

        h2 ('Randomly Generated Links')
        ul {
            names.each { name ->
                li {
                    def href = "/${name}?_t=${new Date().time}".toLowerCase()
                    a(href: href, name)
                }
            }
        }

        img(src: "//${pixel}")

    }
}
# shadow-cljs - browser quickstart

This is a minimum template you can use as the basis for CLJS projects intended to run in the browser.

## Required Software

- [node.js (v6.0.0+)](https://nodejs.org/en/download/)
- [Java JDK (8+)](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [Open JDK (8+)](http://jdk.java.net/10/)

## User Guide

This repository only shows a basic example of how to get a basic Browser build.

Please refer to the full [User Guide](https://shadow-cljs.github.io/docs/UsersGuide.html) for more information.


## Running the Example

```bash
git clone https://github.com/shadow-cljs/quickstart-browser.git quickstart
cd quickstart
npm install
npx shadow-cljs server
```

This runs the `shadow-cljs` server process which all following commands will talk to. Just leave it running and open a new terminal to continue.

The first startup takes a bit of time since it has to download all the dependencies and do some prep work. Once this is running we can get started.

```txt
npx shadow-cljs watch app
```

This will begin the compilation of the configured `:app` build and re-compile whenever you change a file.

When you see a "Build completed." message your build is ready to be used.

```txt
[:app] Build completed. (23 files, 4 compiled, 0 warnings, 7.41s)
```

You can now then open [http://localhost:8020](http://localhost:8020).

The app is only a very basic skeleton with the most useful development tools configured.

`shadow-cljs` is configured by the `shadow-cljs.edn` config. It looks like this:

```clojure
{:source-paths
 ["src"] ;; .cljs files go here

 :dependencies
 [] ;; covered later

 :builds
 {:app {:target :browser
        :output-dir "public/js"
        :asset-path "/js"

        :modules
        {:main ;; <- becomes public/js/main.js
         {:entries [starter.browser]}}

        ;; start a development http server on http://localhost:8020
        :devtools
        {:http-root "public"
         :http-port 8020}
        }}}
```

It defines the `:app` build with the `:target` set to `:browser`. All output will be written to `public/js` which is a path relative to the project root (ie. the directory the `shadow-cljs.edn` config is in).

`:modules` defines the how the output should be bundled together. For now we just want one file. The `:main` module will be written to `public/js/main.js`, it will include the code from the `:entries` and all their dependencies.

`:devtools` configures some useful development things. The `http://localhost:8020` server we used earlier is controlled by the `:http-port` and serves the `:http-root` directory.

The last part is the actual `index.html` that is loaded when you open `http://localhost:8020`. It loads the generated `/js/main.js` and then calls `start.browser.init` which we defined in the `src/start/browser.cljs`.

```html
<!doctype html>
<html>
<head><title>Browser Starter</title></head>
<body>
<h1>shadow-cljs - Browser</h1>
<div id="app"></div>

<script src="/js/main.js"></script>
<script>starter.browser.init();</script>
</body>
</html>
```

## Live reload

To see the live reload in action you can edit the `src/start/browser.cljs`. Some output will be printed in the browser console.

## REPL

During development it the REPL is very useful.

From the command line use `npx shadow-cljs cljs-repl app`.

```
shadow-cljs - config .../shadow-cljs.edn version: 2.2.16
shadow-cljs - connected to server
[2:1]~cljs.user=>
```

This can now be used to eval code in the browser (assuming you still have it open). Try `(js/alert "Hi.")` and take it from there. You might want to use `rlwrap npx shadow-cljs cljs-repl app` if you intend to type a lot here.

You can exit the REPL by either `CTRL+C` or typing `:repl/quit`.

## Release

The `watch` process we started is all about development. It injects the code required for the REPL and the all other devtools but we do not want any of that when putting the code into "production" (ie. making it available publicly).

The `release` action will remove all development code and run the code through the Closure Compiler to produce a minified `main.js` file. Since that will overwrite the file created by the `watch` we first need to stop that.

Use `CTRL+C` to stop the `watch` process and instead run `npx shadow-cljs release app`.

When done you can open `http://localhost:8020` and see the `release` build in action. At this point you would usually copy the `public` directory to the "production" web server.

Note that in the default config we overwrote the `public/js/main.js` created by the `watch`. You can also configure a different path to use for release builds but writing the output to the same file means we do not have to change the `index.html` and test everything as is.


# NOTES

## shadow-cljs is much easier to get started with ClojureScript

### Stuff that is needed to make this work

- `npx shadow-cljs server` starts a nREPL server that you can plugin to from CIDER
- `shadow-cljs watch app` -> For hot compiling and browser reloading
- `cider-connect` to this REPL. Starts a clojure REPL
- `cider-connect-sibling-cljs`, `shadow` RET and `app` RET. This starts a ClojureScript REPL with the current deps. You can `(js/alert "fun")` to find if it works or not.

Note: It is better to include `[[cider/cider-nrepl]]` to the dependencies.

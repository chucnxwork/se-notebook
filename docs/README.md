---
home: true
heroImage: /hero.png
actionText: Get Started →
actionLink: /guide/getting-started.html
footer: MIT Licensed | Copyright © 2018-present Evan You
---

## Links

VuePress is composed of two parts: a [minimalistic static site generator](https://github.com/vuejs/vuepress/tree/master/packages/%40vuepress/core) with a Vue-powered [theming system](../theme/README.md) and [Plugin API](../plugin/README.md), and a [default theme](../theme/default-theme-config.md) optimized for writing technical documentation. It was created to support the documentation needs of Vue’s own sub projects.

## Code

`nuxt generate`

<code-group>
<code-block title="YARN" active>
```bash
yarn create vuepress-site [optionalDirectoryName]
```
</code-block>

<code-block title="NPM">
```bash
npx create-vuepress-site [optionalDirectoryName]
```
</code-block>
</code-group>


```json
{
    "scripts": {
        "docs:dev": "vuepress dev docs",
        "docs:build": "vuepress build docs"
    }
}
```

```
.
├─ docs
│  ├─ README.md
│  └─ .vuepress
│     └─ config.js
└─ package.json
```

``` js{1,4,6-7}
export default { // Highlighted
  data () {
    return {
      msg: `Highlighted!
      This line isn't highlighted,
      but this and the next 2 are.`,
      motd: 'VuePress is awesome',
      lorem: 'ipsum',
    }
  }
}
```


## List

**Built-in Markdown extensions**

* [Table of Contents](../guide/markdown.md#table-of-contents)
* [Custom Containers](../guide/markdown.md#custom-containers)

**List 2**

- Project Name
- Description

**Ordered list**

1. First install [surge](https://www.npmjs.com/package/surge), if you haven’t already.

2. Run `yarn docs:build` or `npm run docs:build`.

3. Deploy to surge by typing `surge docs/.vuepress/dist`.


# Table

|   Relative Path  | Page Routing |
|------------------|--------------|
| /README.md       | /            |
| /guide/README.md | /guide/      |
| /config.md       | /config.html |

## Typography

### Bold
**Bold text**

### Italic

_If your project is using webpack 3.x, you may notice some installation issues with npm. In this case, we recommend using Yarn._\* 

## Alert

::: tip
This is a tip
:::

::: warning
This is a warning
:::

::: danger STOP
This is a dangerous warning
:::

::: details Click me to view the code
This is a details block, which does not work in IE / Edge
:::

> Alert in markdown

<div style="text-align: center">
  <demo-1></demo-1>
</div>

<div class="features">
  <div class="feature">
    <h2>Simplicity First</h2>
    <p>Minimal setup with markdown-centered project structure helps you focus on writing.</p>
  </div>
  <div class="feature">
    <h2>Vue-Powered</h2>
    <p>Enjoy the dev experience of Vue + webpack, use Vue components in markdown, and develop custom themes with Vue.</p>
  </div>
  <div class="feature">
    <h2>Performant</h2>
    <p>VuePress generates pre-rendered static HTML for each page, and runs as an SPA once a page is loaded.</p>
  </div>
</div>

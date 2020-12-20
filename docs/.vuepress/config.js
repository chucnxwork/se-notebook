const { fs, path } = require('@vuepress/shared-utils')

module.exports = ctx => ({
  title: 'Hello VuePress',
  description: 'Just playing around',
  base: '/docs/',
  dest: 'dist',
  configureWebpack: {
    resolve: {
      alias: {
        '@alias': 'docs'
      }
    }
  },
  themeConfig: {
    // logo: '/vuepress-logo.png',
    // lastUpdated: 'Last updated',
    // repo: 'https://github.com/bencodezen/vuepress-starter-kit',
    // docsDir: 'docs',
    // editLinks: true,
    // editLinkText: 'Recommend a change',
    locales: {
        '/': {
            label: 'English',
            selectText: 'Languages',
            ariaLabel: 'Select language',
            editLinkText: 'Edit this page on GitHub',
            lastUpdated: 'Last Updated',
            nav: getNav(),
            sidebar: {
                '/api/': getApiSidebar(),
                '/guide/': getGuideSidebar('Guide', 'Advanced'),
                '/plugin/': getPluginSidebar('Plugin', 'Introduction', 'Official Plugins'),
                '/theme/': getThemeSidebar('Theme', 'Introduction')
            }
        }
    },
    plugins: [
        ['@vuepress/active-header-links'],
        ['@vuepress/back-to-top', true],
        ['@vuepress/pwa', {
            serviceWorker: true,
            updatePopup: true
        }],
        ['@vuepress/medium-zoom', true],
    ]
  }
})

function getNav() {
    return [
        {
          text: 'Guide',
          link: '/guide/'
        },
        {
          text: 'Config Reference',
          link: '/config/'
        },
        {
          text: 'Plugin',
          link: '/plugin/'
        },
        {
          text: 'Theme',
          link: '/theme/'
        },
        {
          text: 'Learn More',
          ariaLabel: 'Learn More',
          items: [
            {
              text: 'API',
              items: [
                {
                  text: 'CLI',
                  link: '/api/cli.html'
                },
                {
                  text: 'Node',
                  link: '/api/node.html'
                }
              ]
            },
            {
              text: 'Contributing Guide',
              items: [
                {
                  text: 'Local Development',
                  link: '/miscellaneous/local-development.html'
                },
                {
                  text: 'Design Concepts',
                  link: '/miscellaneous/design-concepts.html'
                },
                {
                  text: 'FAQ',
                  link: '/faq/'
                },
                {
                  text: 'Glossary',
                  link: '/miscellaneous/glossary.html'
                }
              ]
            },
            {
              text: 'Resources',
              items: [
                {
                  text: '0.x Docs',
                  link: 'https://v0.vuepress.vuejs.org/'
                },
                {
                  text: 'Migrate from 0.x',
                  link: '/miscellaneous/migration-guide.html'
                },
                {
                  text: 'Changelog',
                  link: 'https://github.com/vuejs/vuepress/blob/master/CHANGELOG.md'
                }
              ]
            }
          ]
        }
      ]
}

function getApiSidebar () {
    return [
      'cli',
      'node'
    ]
  }
  
  function getGuideSidebar (groupA, groupB) {
    return [
      {
        title: groupA,
        collapsable: true,
        children: [
          '',
          'getting-started',
          'directory-structure',
        ]
      },
      {
        title: groupB,
        collapsable: false,
        children: [
          'frontmatter',
          'permalinks',
        ]
      }
    ]
  }
  
//   const officalPlugins = fs
//     .readdirSync(path.resolve(__dirname, '../plugin/official'))
//     .map(filename => 'official/' + filename.slice(0, -3))
//     .sort()
  
  function getPluginSidebar (pluginTitle, pluginIntro, officialPluginTitle) {
    return [
      {
        title: pluginTitle,
        collapsable: false,
        children: [
          ['', pluginIntro],
          'using-a-plugin',
          'writing-a-plugin',
          'life-cycle',
          'option-api',
          'context-api'
        ]
      },
    //   {
    //     title: officialPluginTitle,
    //     collapsable: false,
    //     children: officalPlugins
    //   }
    ]
  }
  
  function getThemeSidebar (groupA, introductionA) {
    return [
      {
        title: groupA,
        collapsable: false,
        sidebarDepth: 2,
        children: [
          ['', introductionA],
          'using-a-theme',
          'writing-a-theme',
          'option-api',
          'default-theme-config',
          'blog-theme',
          'inheritance'
        ]
      }
    ]
  }
  

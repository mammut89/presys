var OFF = 0, WARN = 1, ERROR = 2;

module.exports = exports = {
    "root": true,

    "env": {
        "es6": true,
		"browser": true
    },

    "extends": "airbnb",
    "settings": {
        "import/resolver": {
            "webpack": {
                "config": "webpack.config.dev.js"
            }
        }
    },

	"parserOptions": {
		"sourceType": "module",
		"ecmaFeatures": {
			"ecmaVersion": 8,
			"jsx": true,
			"impliedStrict": true,
		}
    },

	"rules" : {
		"linebreak-style": OFF,
		"jsx-a11y/no-static-element-interactions": OFF,
		"import/no-named-as-default": OFF,
        "max-len": [ERROR, 160],
        "no-debugger": WARN,
    }
};

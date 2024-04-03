/*!
* jquery.inputmask.min.js
* https://github.com/RobinHerbots/Inputmask
* Copyright (c) 2010 - 2017 Robin Herbots
* Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php)
* Version: 3.3.11
*/

!function(t){"function"==typeof define&&define.amd?define(["jquery","./inputmask"],t):"object"==typeof exports?module.exports=t(require("jquery"),require("./inputmask")):t(jQuery,window.Inputmask)}(function(t,e){return void 0===t.fn.inputmask&&(t.fn.inputmask=function(i,n){var a,s=this[0];if(void 0===n&&(n={}),"string"==typeof i)switch(i){case"unmaskedvalue":return s&&s.inputmask?s.inputmask.unmaskedvalue():t(s).val();case"remove":return this.each(function(){this.inputmask&&this.inputmask.remove()});case"getemptymask":return s&&s.inputmask?s.inputmask.getemptymask():"";case"hasMaskedValue":return!(!s||!s.inputmask)&&s.inputmask.hasMaskedValue();case"isComplete":return!s||!s.inputmask||s.inputmask.isComplete();case"getmetadata":return s&&s.inputmask?s.inputmask.getmetadata():void 0;case"setvalue":t(s).val(n),s&&void 0===s.inputmask&&t(s).triggerHandler("setvalue");break;case"option":if("string"!=typeof n)return this.each(function(){if(void 0!==this.inputmask)return this.inputmask.option(n)});if(s&&void 0!==s.inputmask)return s.inputmask.option(n);break;default:return n.alias=i,a=new e(n),this.each(function(){a.mask(this)})}else{if("object"==typeof i)return a=new e(i),void 0===i.mask&&void 0===i.alias?this.each(function(){if(void 0!==this.inputmask)return this.inputmask.option(i);a.mask(this)}):this.each(function(){a.mask(this)});if(void 0===i)return this.each(function(){(a=new e(n)).mask(this)})}}),t.fn.inputmask});
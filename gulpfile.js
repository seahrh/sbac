var appName = "sbac";
var gulp = require('gulp');
var $ = require('gulp-load-plugins')();
var runSequence = require('run-sequence');
var del = require('del');
var bases = {
    'pkgs' : 'node_modules/',
    'dist' : 'src/main/webapp/',
    'app' : 'src/main/frontend/'
};
var paths = {
    pages : ['views/**/*.jsp'],
    templates : ['templates/**/*.jspf'],
    scripts : ['scripts/**/*.js'],
    styles : ['styles/**/*.css'],
    images : ['images/**/*.{jpg,png,gif,svg}'],
    fonts : ['fonts/**/*']
};
// Build Production Files, the Default Task
gulp.task('default', function(cb) {
    runSequence('lint', ['scripts', 'images', 'styles', 'templates', 'pages'], cb);
});
// Lint JavaScript
// Fail task if lint throws errors
gulp.task('lint', function() {
    return gulp.src(['gulpfile.js', bases.app + paths.scripts, '!**/ext/*', '!**/vendor/*']).pipe($.jshint()).pipe($.jshint.reporter('jshint-stylish')).pipe($.jshint.reporter('fail'));
    // After updating jshint to 2.0.1,
    // $.jshint.reporter('fail') stops build on error
});
// Clean task group
gulp.task('clean', ['clean:scripts', 'clean:styles', 'clean:images', 'clean:templates', 'clean:pages']);
gulp.task('clean:scripts', function() {
    del.sync([bases.dist + 'scripts']);
});
gulp.task('clean:styles', function() {
    del.sync([bases.dist + 'styles']);
});
gulp.task('clean:images', function() {
    del.sync([bases.dist + 'images']);
});
gulp.task('clean:pages', function() {
    del.sync([bases.dist + 'WEB-INF/views']);
});
gulp.task('clean:templates', function() {
    del.sync([bases.dist + 'WEB-INF/templates']);
});
// Clean fonts only in 'update' task
gulp.task('clean:fonts', function() {
    del.sync([bases.dist + 'fonts']);
});
// Clean Vendor Directories (dependencies) only in 'update' task 
gulp.task('clean:vendor', function() {
    del.sync([bases.app + 'scripts/vendor', bases.app + 'styles/vendor']);
});
gulp.task('images', ['clean:images'], function() {
    return gulp.src(paths.images, {
        cwd : bases.app
    }).pipe(gulp.dest(bases.dist + 'images'));
});
gulp.task('styles', ['clean:styles'], function(cb) {
    // Dependencies must be concatenated in order
    return gulp.src(['vendor/bootstrap.css', 'app/*.css', 'view/*.css'], {
        cwd : bases.app + 'styles/'
    }).pipe($.concat(appName + '.all.css')).pipe($.cleanCss({
        keepSpecialComments : 0
    })).pipe($.rename(appName + '.min.css')).pipe(gulp.dest(bases.dist + 'styles'));
});
// Concatenate and minify scripts
gulp.task('scripts:catmin', function() {
    var srcs, deps, appScripts, pageScripts;
    
    deps = ['vendor/jquery.js', 'vendor/bootstrap.js', 'vendor/bootstrap3-typeahead.js'];
    
    appScripts = ['app/base.js', 'app/json.js'];
    
    pageScripts = ['view/index.js'];
    
    srcs = deps.concat(appScripts, pageScripts, 'app/page-controller.js');
    
    return gulp.src(srcs, {
        cwd : bases.app + 'scripts'
    }).pipe($.concat(appName + ".all.js")).pipe($.uglify()).pipe($.rename(appName + '.min.js')).pipe(gulp.dest(bases.dist + 'scripts'));
});
gulp.task('scripts', ['clean:scripts'], function(cb) {
    runSequence(['scripts:catmin'], cb);
});
// Copies pages into the war dir
gulp.task('pages', ['clean:pages'], function() {
    return gulp.src(paths.pages, {
        cwd : bases.app
    }).pipe(gulp.dest(bases.dist + 'WEB-INF/views'));
});
// Copies templates into the war dir
gulp.task('templates', ['clean:templates'], function() {
    return gulp.src(paths.templates, {
        cwd : bases.app
    }).pipe(gulp.dest(bases.dist + 'WEB-INF/templates'));
});
gulp.task('watch', function() {
    var watcher;
    watcher = gulp.watch([bases.app + paths.scripts], []);
    watcher.on('change', function(event) {
        console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
        runSequence('lint', 'scripts', 'reload');
    });
    watcher = gulp.watch([bases.app + paths.pages], []);
    watcher.on('change', function(event) {
        console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
        runSequence('pages', 'reload');
    });
    watcher = gulp.watch([bases.app + paths.templates], []);
    watcher.on('change', function(event) {
        console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
        runSequence('templates', 'reload');
    });
    watcher = gulp.watch([bases.app + paths.styles], []);
    watcher.on('change', function(event) {
        console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
        runSequence('styles', 'reload');
    });
});
// Reload webapp when watched files are changed
gulp.task('reload', $.shell.task(['mvn war:war']));
gulp.task('update:jquery', function() {
    return gulp.src(['jquery.js'], {
        cwd : bases.pkgs + 'jquery/dist'
    }).pipe(gulp.dest(bases.app + 'scripts/vendor'));
});
gulp.task('update:bootstrap-js', function() {
    return gulp.src(['bootstrap.js'], {
        cwd : bases.pkgs + 'bootstrap/dist/js'
    }).pipe(gulp.dest(bases.app + 'scripts/vendor'));
});
gulp.task('update:bootstrap-css', function() {
    return gulp.src(['bootstrap.css'], {
        cwd : bases.pkgs + 'bootstrap/dist/css'
    }).pipe(gulp.dest(bases.app + 'styles/vendor'));
});
gulp.task('update:bootstrap-3-typeahead', function() {
    return gulp.src(['bootstrap3-typeahead.js'], {
        cwd : bases.pkgs + 'bootstrap-3-typeahead'
    }).pipe(gulp.dest(bases.app + 'scripts/vendor'));
});
gulp.task('update', function(cb) {
    runSequence(['clean:vendor'], ['update:jquery', 'update:bootstrap-js', 'update:bootstrap-css', 'update:bootstrap-3-typeahead'], cb);
});
function error(err) {
    err.showStack = true;
    $.util.log($.util.colors.yellow(err));
    $.util.beep();
    // Signal 'end' event so that gulp watch will not break
    this.emit('end');
}
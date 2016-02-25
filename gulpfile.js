/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var gulp = require('gulp');
var useref = require('gulp-useref');
var uglify = require('gulp-uglify');
var gulpIf = require('gulp-if');

gulp.task('default', function(){
  return gulp.src('web/**/*.html')
    .pipe(useref())
    .pipe(gulpIf('*.js', uglify()))    
    .pipe(gulp.dest('build/web'));
});

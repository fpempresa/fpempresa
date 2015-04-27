module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        clean: ['.tmp'],
        useminPrepare: {
            html: 'web/**/*.html',
            options: {
                staging:"tmp",
                dest: 'build/web',
                steps: { 
                    js: ['concat'],
                    css:[]
                }
            }
        },
        concat: {
            options: {
                separator: ';'
            }
        }, 
        copy: {
            main: {
                cwd: 'tmp/concat', 
                src: '**/*',
                dest: 'build/web',
                expand: true
            }
        }, 
        usemin: {
            html: ['build/web/**/*.html'],
            options: {
                assetsDirs: ['build/web/**']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-usemin');


    grunt.registerTask('default', [
        'useminPrepare', 'concat','copy:main','usemin'
    ]);
};
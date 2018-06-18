/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { TrabajoMySuffixComponent } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.component';
import { TrabajoMySuffixService } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.service';
import { TrabajoMySuffix } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.model';

describe('Component Tests', () => {

    describe('TrabajoMySuffix Management Component', () => {
        let comp: TrabajoMySuffixComponent;
        let fixture: ComponentFixture<TrabajoMySuffixComponent>;
        let service: TrabajoMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TrabajoMySuffixComponent],
                providers: [
                    TrabajoMySuffixService
                ]
            })
            .overrideTemplate(TrabajoMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrabajoMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrabajoMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TrabajoMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.trabajos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
